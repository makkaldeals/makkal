package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role
import com.makkaldeals.auth.Customer
import com.makkaldeals.auth.Post
import com.makkaldeals.auth.Business


class CustomerController {

  def springSecurityService;
  static navigation = true;
  def index = {

    redirect(controller: 'login', action: 'index', params: ["targetUrl": "/customer/showPosts", "role": Role.ROLE_CUSTOMER])
  }

  @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
  def welcome = {
    render view: 'welcome'
  }

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

    Business business = Business.findByName(params.business);
    //TODO implement pagination
    def posts = Post.withCriteria {
     
      author {
        'in' ('business', business)
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
