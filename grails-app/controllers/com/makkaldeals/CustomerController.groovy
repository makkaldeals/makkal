package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role


class CustomerController {

  def springSecurityService;
  static navigation = true;

  def index = {

    redirect(controller:'login' ,action:'index' , params:["targetUrl":"/customer/welcome", "role":Role.ROLE_CUSTOMER])
  }

  @Secured(['ROLE_CUSTOMER' , 'ROLE_ADMIN'])
  def welcome = {
    def user = springSecurityService.currentUser;
    render (view : 'welcome' , model : [user:user]) ;
  }
}