package com.makkaldeals.auth


import groovy.text.SimpleTemplateEngine

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import grails.plugins.springsecurity.ui.AbstractS2UiController

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class RegisterController extends AbstractS2UiController {

	static defaultAction = 'index'

	def mailService
	def saltSource

	def index = {
		[command: new RegisterCommand()]
	}

	def register = { RegisterCommand command ->

		if (command.hasErrors()) {
			render view: 'index', model: [command: command]
			return
		}

		String salt = saltSource instanceof NullSaltSource ? null : command.email
		String password = springSecurityService.encodePassword(command.password, salt)
		def user = lookupUserClass().newInstance(email: command.email,
				password: password, areaCode:command.areaCode, accountLocked: true, enabled: true)
		if (!user.validate() || !user.save()) {
			// TODO
		}

		def registrationCode = new RegistrationCode(username: user.email).save()
		String url = generateLink('verifyRegistration', [t: registrationCode.token])

		def conf = SpringSecurityUtils.securityConfig
		def body = conf.ui.register.emailBody
       
		if (body.contains('$')) {
			body = evaluate(body, [user: user, url: url])
		}
		mailService.sendMail {
			to command.email
			from conf.ui.register.emailFrom
			subject conf.ui.register.emailSubject
			html body.toString()
		}

		render view: 'index', model: [emailSent: true]
	}

	def verifyRegistration = {

		def conf = SpringSecurityUtils.securityConfig
		String defaultTargetUrl = conf.successHandler.defaultTargetUrl

		String token = params.t

		def registrationCode = token ? RegistrationCode.findByToken(token) : null
		if (!registrationCode) {
			flash.error = message(code: 'spring.security.ui.register.badCode')
			redirect uri: defaultTargetUrl
			return
		}

		def user
		RegistrationCode.withTransaction { status ->
			user = lookupUserClass().findByEmail(registrationCode.username)
			if (!user) {
				return
			}
			user.accountLocked = false
			user.save()
			def UserRole = lookupUserRoleClass()
			def Role = lookupRoleClass()
			for (roleName in conf.ui.register.defaultRoleNames) {
				UserRole.create user, Role.findByAuthority(roleName)
			}
			registrationCode.delete()
		}

		if (!user) {
			flash.error = message(code: 'spring.security.ui.register.badCode')
			redirect uri: defaultTargetUrl
			return
		}

		springSecurityService.reauthenticate user.email

		flash.message = message(code: 'spring.security.ui.register.complete')
        println "verificaition complete redirecting to ${defaultTargetUrl} ${conf.ui.register.postRegisterUrl} "
		redirect uri: conf.ui.register.postRegisterUrl ?: defaultTargetUrl
	}

	def forgotPassword = {

		if (!request.post) {
			// show the form
			return
		}

		String email = params.email
		if (!email) {
			flash.error = message(code: 'spring.security.ui.forgotPassword.username.missing')
			return
		}

		def user = lookupUserClass().findByEmail(email)
		if (!user) {
			flash.error = message(code: 'spring.security.ui.forgotPassword.user.notFound')
			return
		}
     	def registrationCode = new RegistrationCode(username: user.email).save()
     	String url = generateLink('resetPassword', [t: registrationCode.token])

		def conf = SpringSecurityUtils.securityConfig
		def body = conf.ui.forgotPassword.emailBody
		if (body.contains('$')) {
			body = evaluate(body, [user: user, url: url])
		}
      
       mailService.sendMail {
			to user.email
			from conf.ui.forgotPassword.emailFrom
			subject conf.ui.forgotPassword.emailSubject
			html body.toString()
		}

		[emailSent: true]
	}

	def resetPassword = { ResetPasswordCommand command ->

		String token = params.t

		def registrationCode = token ? RegistrationCode.findByToken(token) : null
		if (!registrationCode) {
			flash.error = message(code: 'spring.security.ui.resetPassword.badCode')
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
			return
		}

		if (!request.post) {
			return [token: token, command: new ResetPasswordCommand()]
		}

		command.email = registrationCode.username
		command.validate()

		if (command.hasErrors()) {
			return [token: token, command: command]
		}

		String salt = saltSource instanceof NullSaltSource ? null : registrationCode.username
		RegistrationCode.withTransaction { status ->
			def user = lookupUserClass().findByEmail(registrationCode.username)
			user.password = springSecurityService.encodePassword(command.password, salt)
			user.save()
			registrationCode.delete()
		}

		springSecurityService.reauthenticate registrationCode.username

		flash.message = message(code: 'spring.security.ui.resetPassword.success')

		def conf = SpringSecurityUtils.securityConfig
		String postResetUrl = conf.ui.register.postResetUrl ?: conf.successHandler.defaultTargetUrl
		redirect uri: postResetUrl
	}

	protected String generateLink(String action, linkParams) {
		createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
				controller: 'register', action: action,
				params: linkParams)

	}

	protected String evaluate(s, binding) {
		new SimpleTemplateEngine().createTemplate(s).make(binding)
	}

	static final passwordValidator = { String password, command ->
		if (command.email && command.email.equals(password)) {
			return 'command.password.error.username'
		}

		if (password && password.length() >= 8 && password.length() <= 64 &&
				(!password.matches('^.*\\p{Alpha}.*$') ||
				!password.matches('^.*\\p{Digit}.*$'))) {
			return 'command.password.error.strength'
		}
	}

    static final areaCodeValidator = { String areaCode, command ->
      if (!areaCode.isInteger()){
        return 'command.areacode.error'
      }
    }

	
}

class RegisterCommand {

	String email
	String password
  //todo validate areacode
    String areaCode

	static constraints = {

		email blank: false, email: true
		password blank: false, minSize: 8, maxSize: 64, validator: RegisterController.passwordValidator
        areaCode blank: false, validator: RegisterController.areaCodeValidator
	}
}

class ResetPasswordCommand {
	String email
	String password

	static constraints = {
		password blank: false, minSize: 8, maxSize: 64, validator: RegisterController.passwordValidator

	}
}
