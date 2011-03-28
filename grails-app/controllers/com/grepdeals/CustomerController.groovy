package com.grepdeals

import grails.plugins.springsecurity.Secured
import com.grepdeals.auth.Role


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
