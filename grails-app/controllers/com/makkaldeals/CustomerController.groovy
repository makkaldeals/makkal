package com.makkaldeals

import grails.plugins.springsecurity.Secured
import com.makkaldeals.auth.Role

@Secured(['ROLE_CUSTOMER' , 'ROLE_ADMIN'])
class CustomerController {

  def springSecurityService;
                                                        
  /*def index = {

    redirect(controller:'login' ,action:'index' , params:["targetUrl":"/customer/welcome", "role":Role.ROLE_CUSTOMER])
  }  */

  def welcome = {
    def user = springSecurityService.currentUser;
    render "Customer controller : Welcome ${user.email} ! : ${user.areaCode} -> ${user.getAuthorities()}";
  }
}
