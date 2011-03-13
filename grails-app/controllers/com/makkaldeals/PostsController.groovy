package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role
import com.makkaldeals.auth.Customer
import com.makkaldeals.auth.Post
import com.makkaldeals.auth.Business

class PostsController {

  def springSecurityService;
  def emailService;
  
  def index = { redirect action: showPosts }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def showPosts = {

    def author = params.author ? Customer.findByEmail(params.author) : springSecurityService.currentUser;
    def posts = Post.withCriteria {
      eq 'author', author
      eq 'published', true
      order "dateCreated", "desc"
      cache true
    }

    render(view: 'showPosts', model: [posts: posts]);

  }



  @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
  def createPost = {
    def post = new Post(request.method == 'POST' ? params['post'] : [:])
    render view: "createPost", model: [post: post]
  }

  @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
  def editPost = {
    def post = Post.get(params.id)
    if (post) {
      render view: "createPost", model: [post: post]
    }
    else {
      response.sendError 404
    }
  }

  @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
  def publishPost = {

    //TODO: HANDLE SAVE BUTTON IN RICH TEXT EDITOR.
    //TODO: IMPLEMENT IMAGE UPLOADING
    def post = Post.get(params.id) ?: new Post()
    Post.withTransaction {
      post.properties = params['post']
      post.author = springSecurityService.currentUser;
      post.published = true
      if (post.save()) {
        post.parseTags(params.tags, ",")
        emailService.sendPostConfirmation(post);
        redirect(action: 'showPosts');
      }
      else {
        render view: "createPost", model: [post: post]
      }
    }


  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def postsByTag = {
    if (params.tag) {
      //TODO implement pagination
      //def posts = Post.findAllByTag(params.tag.trim(), [max: 10, offset: params.offset, sort: "dateCreated", order: "desc"])
      def posts = Post.findAllByTag(params.tag.trim(), [sort: "dateCreated", order: "desc"])
      posts = posts.findAll { it.published };
      render(view: 'showPosts', model: [posts: posts]);

    }
    else {
      redirect action: "showPosts"
    }
  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def postsByBusiness = {

    //TODO: Look for better way to do this query
    String city = params.city;
    String areaCode = params.areaCode;

    def business;

    if (!city && !areaCode){
      business = Business.findAllByName(params.business);
    }
    else if (city && areaCode){
      business = Business.withCriteria {
        ilike 'city', city
        eq 'areaCode' , areaCode
      }
    }else if (city) {
      business = Business.findAllByNameAndCityIlike(params.business, city)
    }else if (areaCode){
      business  = Business.findAllByNameAndAreaCode(params.business, areaCode);
    }

    //TODO implement pagination
    def posts = Post.withCriteria {

      author {
        'in'('business', business)
      }
      eq 'published', true
      order "dateCreated", "desc"
      cache true
    }
    render(view: 'showPosts', model: [posts: posts]);

  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def showPost = {
    def post = Post.get(params.id)
    if (post) {
      render view: "showPost", model: [post: post]
    }
    else {
      response.sendError 404
    }
  }

}
