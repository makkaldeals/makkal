package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role

@Secured(['ROLE_CLIENT' , 'ROLE_ADMIN'])
class ClientController {

  def springSecurityService;

  /*def index = {

    redirect(controller:'login' ,action:'index' , params:["targetUrl":"/client/welcome", "role":Role.ROLE_CLIENT])
  } */

  def welcome = {
    def user = springSecurityService.currentUser;
    render "Client controller : Welcome ${user.email} ! : ${user.areaCode} -> ${user.getAuthorities()}";
  }
}
