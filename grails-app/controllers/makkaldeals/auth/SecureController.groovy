package makkaldeals.auth

import grails.plugins.springsecurity.Secured

class SecureController {

	@Secured(['ROLE_ADMIN']) 
	def admins = { render 'Logged in with ROLE_ADMIN' }

	@Secured(['ROLE_USER']) 
	def users = { render 'Logged in with ROLE_USER' }
}
