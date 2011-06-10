package com.grepdeals

import com.grepdeals.auth.User
import grails.plugins.springsecurity.Secured

class SettingsController {

    def userService

    def facebookGraphService

    def index = {

        redirect action: 'showSettings';
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def showSettings = {
        render view: 'settings';
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def updateSettings = {

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
