package com.makkaldeals

import grails.plugins.springsecurity.Secured

class PostsController {

  def springSecurityService;
  def postService;

  def index = { redirect action: showPosts }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def showPosts = {

    render(view: 'showPosts', model: [posts: postService.findPostsByAuthor(params.author)]);

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

    //TODO: HANDLE SAVE BUTTON IN RICH TEXT EDITOR (ckeditor.com).
    //TODO: IMPLEMENT IMAGE UPLOADING

    Post post = postService.create(params);
    if (!post.hasErrors()) {
      redirect(action: 'showPosts');
    }
    else {
      render view: "createPost", model: [post: post]
    }
  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def postsByTag = {
    if (params.tag) {
      //TODO implement pagination
      render(view: 'showPosts', model: [posts: postService.findPostsByTag(params.tag)]);

    }
    else {
      redirect action: "showPosts"
    }
  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def postsByBusiness = {

    //TODO: Look for better way to do this query
    
    render(view: 'showPosts', model: [posts: postService.findPostsByBusiness(params)]);

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
