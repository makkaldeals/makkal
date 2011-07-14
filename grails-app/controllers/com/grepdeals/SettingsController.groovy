package com.grepdeals

import com.grepdeals.auth.User
import grails.plugins.springsecurity.Secured

class SettingsController {

	def saltSource
	
    def userService

    def facebookGraphService
	
	def springSecurityService

    def index = {

        redirect action: 'showSettings';
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def showSettings = {
        render view: 'settings';
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def updateSettings = {
		def currentPassword = springSecurityService.currentUser.password
		String existingPassword = springSecurityService.encodePassword(params.oldPassword);
		def newPassword = params.newPassword
		def confirmPassword = params.confirmPassword
		
		if (!(params.oldPassword.isEmpty())) {
			if (newPassword == null || newPassword.isEmpty()) {
				flash.message = message(code: 'user.newpassword.null')
				render view: 'settings';
				return
			}
			
			if (!(newPassword.equals(confirmPassword))) {
				flash.message = message(code: 'user.newAndConfirmPassword.not.equal')
				render view: 'settings';
				return
	
			}
		
			if (!(currentPassword.equals (existingPassword))) {
				flash.message = message(code: 'user.oldpassword.mismatch')
				render view: 'settings'
				return
			}
		}
        User updatedUser = userService.update(params)
        session.user = updatedUser
        if (updatedUser.hasErrors()) {
            flash.error = message(code: 'user.transaction.unsuccessful')
        } else {
            flash.message = message(code: 'user.transaction.successful')
        }
        render view: 'settings';
    }

    def show = {

      
        render view: 'settings';

    }
	

}
