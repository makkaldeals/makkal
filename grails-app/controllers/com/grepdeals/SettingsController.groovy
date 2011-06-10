package com.grepdeals

import com.grepdeals.auth.User
import grails.plugins.springsecurity.Secured

class SettingsController {

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
		
		def newPassword = params.newPassword
		def confirmPassword = params.confirmPassword
		System.out.println("New Password "+newPassword);
		if (newPassword == null || newPassword.isEmpty()) {
			flash.message = message(code: 'user.newpassword.null')
			render view: 'settings';
			return
		}
		
		if (params.oldPassword != null) {
			//def oldPassword = springSecurityService.encodePassword(params.oldPassword, salt)
			
			if (!(newPassword.equals(confirmPassword))) {
				flash.message = message(code: 'user.newAndConfirmPassword.not.equal')
				render view: 'settings';
				return
	
			}
		
			if (!(currentPassword.equals (params.oldPassword))) {
				System.out.println("we should be here")
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

        if (facebookGraphService != null) {
            System.out.println(session.facebook.uid)

            System.out.println(facebookGraphService.getFacebookData())

            //System.out.println(facebookGraphService.getFriends())

            // facebookGraphService.api()

            //redirect(action: '\showPosts');
            // session.facebook.;

            //facebookGraphService.publishWall()
            System.out.println(session.facebook.uid)
        }
        render view: 'settings';

    }
	

}
