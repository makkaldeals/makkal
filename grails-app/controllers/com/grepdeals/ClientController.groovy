package com.grepdeals

import com.grepdeals.auth.Role
import grails.plugins.springsecurity.Secured

class ClientController {

    def springSecurityService;
    def postService;


    //TODO Add security
    def index = {
       // redirect(controller: 'login', action: 'index', params: ["targetUrl": "/client/welcome", "role": Role.ROLE_CLIENT])
        redirect(action: 'welcome');
    }



    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def welcome = {
        def results = postService.findTodayPosts(params);
        render(view: 'welcome', model: [posts: results.list, totalCount: results.totalCount]);


    }

     @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def oldPosts = {
        def results = postService.findOldPosts(params);
        render(view: 'welcome', model: [posts: results.list, totalCount: results.totalCount]);


    }


}
