package com.grepdeals

import grails.plugins.springsecurity.Secured

class PostsController {

  def springSecurityService;
  def postService;

  def index = { redirect action: showPosts }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def showPosts = {

    def results =  postService.findPostsByAuthor(params);
    render(view: 'showPosts', model: [posts:results.list, totalCount:results.totalCount]);

  }

  def showPending = {
	  def results =  postService.findPostsByAuthor(params);
	  render(view: 'showPosts', model: [posts:results.list, totalCount:results.totalCount, confirmationMessage: message(code: 'spring.security.ui.approval.pending')]);
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

    log.info(params);
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
      def results =   postService.findPostsByTag(params);
      render(view: 'showPosts', model: [posts: results.list, totalCount:results.totalCount]);

    }
    else {
      redirect action: "showPosts"
    }
  }

  @Secured(['IS_AUTHENTICATED_REMEMBERED'])
  def postsByBusiness = {

    def results =  postService.findPostsByBusiness(params);

    if (!results){
        log.warn("No posts match business ${params.business}, redirecting to showPosts");
        redirect action: "showPosts";
    }else{
        render(view: 'showPosts', model: [posts:results.list, totalCount:results.totalCount]);
    }


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
