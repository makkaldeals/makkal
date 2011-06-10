package com.grepdeals

import com.grepdeals.auth.Role
import grails.plugins.springsecurity.Secured

class ClientController {

    def springSecurityService;
    def postService;


    def index = {
        redirect(controller: 'login', action: 'index', params: ["targetUrl": "/client/welcome", "role": Role.ROLE_CLIENT])
    }



    @Secured(['ROLE_CLIENT', 'ROLE_ADMIN'])
    def welcome = {
        def results = postService.findTodayPosts(params);
        render(view: 'welcome', model: [posts: results.list, totalCount: results.totalCount]);


    }

    @Secured(['ROLE_CLIENT', 'ROLE_ADMIN'])
    def oldPosts = {
        def results = postService.findOldPosts(params);
        render(view: 'welcome', model: [posts: results.list, totalCount: results.totalCount]);


    }


}
