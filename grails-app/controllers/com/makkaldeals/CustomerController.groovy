package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role
import com.makkaldeals.auth.Customer
import com.makkaldeals.auth.Post


class CustomerController {

  def springSecurityService;
  static navigation = true;

  def index = {

    redirect(controller: 'login', action: 'index', params: ["targetUrl": "/customer/welcome", "role": Role.ROLE_CUSTOMER])
  }

  @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
  def welcome = {
    render view: 'welcome'
  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def showMyPosts = {
    Customer user = springSecurityService.currentUser;
    render(view: 'showPosts', model: [posts: user.posts]);

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

    def post = Post.get(params.id) ?: new Post()
    Post.withTransaction {
      post.properties = params['post']
      post.author = springSecurityService.currentUser;
      post.published = true
      if (post.save()) {
        post.parseTags(params.tags, ",")
        //redirect(action: "showEntry", params: [title: entry.title, author: entry.author])
        redirect(action: 'showMyPosts');
      }
      else {
        render view: "/createPost", model: [post: post]
      }
    }

  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def postsByTag = {
    if (params.tag) {
      def posts = Post.findAllByTag(params.tag.trim(), [max: 10, offset: params.offset, sort: "dateCreated", order: "desc"])
      posts = posts.findAll { it.published };
      render(view: 'showPosts', model: [posts: posts]);

    }
    else {

      redirect action: "showMyPosts"
    }
  }
}
