package com.grepdeals

import grails.plugins.springsecurity.Secured;
import com.grepdeals.auth.User
import com.grepdeals.auth.UserService;

class SettingsController {

	def userService
	
    static navigation = true;

	def index = {

      redirect action:'showSettings';
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def showSettings = {
      render view:'settings';
    }
	
	@Secured(['ROLE_CLIENT', 'ROLE_ADMIN'])
	def updateSettings = {
  
	  //log.info(params);
	  userService.update(params)
	  render view:'settings';
	}
}
