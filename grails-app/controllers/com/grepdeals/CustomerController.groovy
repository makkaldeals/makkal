package com.grepdeals

import com.grepdeals.auth.Role
import grails.plugins.springsecurity.Secured

class CustomerController {


    def index = {

        redirect(controller: 'login', action: 'index', params: ["targetUrl": "/posts/showPosts", "role": Role.ROLE_CUSTOMER])
    }

    @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
    def welcome = {
        render view: 'welcome'
    }


}
