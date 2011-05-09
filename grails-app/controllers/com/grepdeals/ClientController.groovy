package com.grepdeals

import grails.plugins.springsecurity.Secured
import com.grepdeals.auth.Role
import com.grepdeals.PostService

class ClientController {

    static navigation = true;
    def springSecurityService;
    def postService;


    def index = {
        redirect(controller: 'login', action: 'index', params: ["targetUrl": "/client/welcome", "role": Role.ROLE_CLIENT])
    }



    @Secured(['ROLE_CLIENT', 'ROLE_ADMIN'])
    def welcome = {
        //  render view: 'welcome';
        def results = postService.findAllPosts(params);
        render(view: 'welcome', model: [posts: results.list, totalCount: results.totalCount]);


    }

}
