package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role

class ClientController {

  static navigation = true;
  def springSecurityService;

  def index = {

    redirect(controller:'login' ,action:'index' , params:["targetUrl":"/client/welcome", "role":Role.ROLE_CLIENT])
  }

  @Secured(['ROLE_CLIENT' , 'ROLE_ADMIN'])
  def welcome = {
    render view:'welcome' ;
  }
}
