package com.grepdeals

import com.grepdeals.auth.Role
import grails.plugins.springsecurity.Secured

class CustomerController {

    def springSecurityService;

    def index = {
		def user = springSecurityService.currentUser
       if (springSecurityService.isLoggedIn()){
             List myRoles = springSecurityService.currentUser.getAuthorities().collect {it.authority}  ;

             if (myRoles.contains(Role.ROLE_CUSTOMER)) {
                redirect(controller: 'posts', action: 'showPosts' );
             } else{
			    if(user instanceof com.grepdeals.auth.Customer) {
					redirect(controller: 'posts', action: 'showPending');
			    } else{
                	redirect(controller: 'register' , action: 'newCustomer');
			    }
             }
     }else{
           params.targetUrl = "/customer/index"  ;
           redirect (controller:'login' , action: 'auth', params: params);
      }

    }

    @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
    def welcome = {
        render view: 'welcome'
    }

     @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def register = {

        redirect(controller: 'login', action: 'index', params: ["targetUrl": "/posts/showPosts", "role": Role.ROLE_CUSTOMER])
    }

	private String getViewByRole(String role){
		return (role == Role.ROLE_CLIENT) ? 'newUser' : 'newCustomer';
	}
}
