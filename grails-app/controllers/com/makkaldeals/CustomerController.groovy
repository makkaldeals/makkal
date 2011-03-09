package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role


class CustomerController {

  
  static navigation = true;
  def index = {

    redirect(controller: 'login', action: 'index', params: ["targetUrl": "/posts/showPosts", "role": Role.ROLE_CUSTOMER])
  }

  @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
  def welcome = {
    render view: 'welcome'
  }

  
}
