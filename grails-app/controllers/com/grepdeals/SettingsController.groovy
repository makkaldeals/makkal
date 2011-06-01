package com.grepdeals

import grails.plugins.springsecurity.Secured

import com.grepdeals.auth.User

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
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def updateSettings = {
  
	  User updatedUser = userService.update(params)
	  session.user = updatedUser
	  if(updatedUser.hasErrors()) {
		  flash.error = message(code: 'user.transaction.unsuccessful')
	  } else {
	  	flash.message = message(code: 'user.transaction.successful')
	  }
	  render view:'settings';
	}
}
