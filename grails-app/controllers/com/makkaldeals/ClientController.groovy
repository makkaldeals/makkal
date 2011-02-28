package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role

@Secured(['ROLE_CLIENT' , 'ROLE_ADMIN'])
class ClientController {

  def springSecurityService;

  def welcome = {
    def user = springSecurityService.currentUser;
    render (view : 'welcome' , model : [user:user]) ;


  }
}
