package com.makkaldeals.auth


import groovy.text.SimpleTemplateEngine

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import grails.plugins.springsecurity.ui.AbstractS2UiController
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH;


class RegisterController extends AbstractS2UiController {

  static defaultAction = 'index'

  def mailService
  def saltSource

  def index = {
    [command: new RegisterCommand()]
  }

  def register = {RegisterCommand command ->

    String role = params.role;
    log.info("Registering with role ${role}");
    if (command.hasErrors()) {
      render view: 'index', model: [command: command]
      return
    }


    def userClass = lookupUserClass();
    def customerClass = lookupUserClass(Role.ROLE_CUSTOMER);
    def roleClass = lookupRoleClass();
    def userRoleClass = lookupUserRoleClass();
    def user = userClass.findByEmail(command.email);
    def roleInstance = roleClass.findByAuthority(role);
    //Handle already exisiting user.
    if (user) {
      log.info("Handing registration for existing user ${user.email}");
      if (userRoleClass.findByUserAndRole(user, roleInstance)) {
        log.error("User ${command.email} with role ${role} already exists");
        flash.message = message(code: 'spring.security.ui.register.user.exists', args: [command.email]);
        redirect(url: request.getHeader('Referer'));
      }
      else {
        log.info("User ${command.email} will be added to role ${role}");
        if (role.equals(Role.ROLE_CLIENT)) {
          UserRole.create(user, roleInstance);
          springSecurityService.reauthenticate user.email
          redirect uri: params.targetUrl;
        }
        else if (role.equals(Role.ROLE_CUSTOMER)) {

          //FIXME: Find better way to do this, this currently deletes existing client user and creates
          // new instance of customer user, which is not good.
          log.info("Existing user ${command.email} registered as ${role}, needs approval");
          //don't update password. use existing password in account
          String password = user.password;
          //delete existing user with ROLE_CLIENT
          def clientRoleInstance = Role.findByAuthority(Role.ROLE_CLIENT);
          userRoleClass.remove(user, clientRoleInstance, true);
          user.delete(flush:true);
          //create user as ROLE_CUSTOMER
          user = customerClass.
                  newInstance(email: command.email,
                  password: password,
                  firstName: command.firstName,
                  lastName: command.lastName,
                  businessName: command.businessName,
                  category: command.category,
                  address: command.address,
                  city: command.city,
                  state: command.state,
                  areaCode: command.areaCode,
                  country: command.country,
                  phone: command.phone,
                  website: command.website,
                  accountLocked: true,
                  enabled: true)

          if (!user.validate() || !user.save()) {
            log.error("Error in validating or saving user ${user.errors}")
          }
          //since user was previously client, add ROLE_CLIENT
          userRoleClass.create(user, clientRoleInstance);
          generateApproval(user, role, params.targetUrl);
        }
        else {
          log.error("This should never happen");
          log.error("Invalid role ${role}");
          render view: 'index';
        }

      }

    }
    //Handle new user
    else {
      log.info("Handling registratin for new user ${command.email}");
      String salt = saltSource instanceof NullSaltSource ? null : command.email
      String password = springSecurityService.encodePassword(command.password, salt)

      if (role.equals(Role.ROLE_CLIENT)) {

        user = userClass.newInstance(email: command.email,
              password: password,
              areaCode: command.areaCode,
              accountLocked: true,
              enabled: true
              )
        if (!user.validate() || !user.save()) {
          log.error("Error in validating or saving user ${user.errors}")
        }
        chain(action: approveRegistration, params: [email: command.email, role: role, targetUrl: params.targetUrl])

      }
      else if (role.equals(Role.ROLE_CUSTOMER)) {

        user = customerClass.
              newInstance(email: command.email,
              password: password,
              firstName: command.firstName,
              lastName: command.lastName,
              businessName: command.businessName,
              category: command.category,
              address: command.address,
              city: command.city,
              state: command.state,
              areaCode: command.areaCode,
              country: command.country,
              phone: command.phone,
              website: command.website,
              accountLocked: true,
              enabled: true)

        if (!user.validate() || !user.save()) {
          log.error("Error in validating or saving user ${user.errors}")
        }

        generateApproval(user, role, params.targetUrl);
      }
      else {
        log.error("Invalid role ${role}");
        render view: 'index';
      }
    }


  }

  private void generateApproval(User user, String role, String targetUrl) {

    def conf = SpringSecurityUtils.securityConfig;
    String url = generateLink('approveRegistration', [role: role, email: user.email, targetUrl: targetUrl])

    def body = conf.ui.approve.emailBody

    if (body.contains('$')) {
      body = evaluate(body, [user: user, url: url])
    }
    mailService.sendMail {
      to CH.config.makkaldeals.user.admin.email
      from conf.ui.approve.emailFrom
      subject conf.ui.approve.emailSubject
      html body.toString()
    }
    render view: 'index', model: [confirmationMessage: message(code: 'spring.security.ui.approval.sent')]

  }

  def approveRegistration = {

    String email = params.email;
    String role = params.role;
    String targetUrl = params.targetUrl;

    def registrationCode = new RegistrationCode(username: email).save()
    String url = generateLink('verifyRegistration', [t: registrationCode.token, role: role, targetUrl: targetUrl])

    def conf = SpringSecurityUtils.securityConfig;
    def body = conf.ui.register.emailBody

    if (body.contains('$')) {
      body = evaluate(body, [url: url])
    }
    mailService.sendMail {
      to email
      from conf.ui.register.emailFrom
      subject conf.ui.register.emailSubject
      html body.toString()
    }
    render view: 'index', model: [confirmationMessage: message(code: 'spring.security.ui.register.sent', args: [email])]
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
    RegistrationCode.withTransaction {status ->
      def userClass = lookupUserClass(params.role);
      user = userClass.findByEmail(registrationCode.username)
      if (!user) {
        log.info("Could not verify registration. No user found with email ${registrationCode.username} in class ${userClass} ")
        return
      }
      user.accountLocked = false
      user.save()
      def UserRole = lookupUserRoleClass()
      def Role = lookupRoleClass()
      UserRole.create user, Role.findByAuthority(params.role);

      registrationCode.delete()
    }

    if (!user) {
      flash.error = message(code: 'spring.security.ui.register.badCode')
      redirect uri: defaultTargetUrl
      return
    }

    springSecurityService.reauthenticate user.email

    flash.message = message(code: 'spring.security.ui.register.complete')
    log.info("verificaition complete redirecting to ${params.targetUrl} ");
    redirect uri: params.targetUrl;
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
    String url = generateLink('resetPassword', [t: registrationCode.token, targetUrl: params.targetUrl])

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

  def resetPassword = {ResetPasswordCommand command ->

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
    RegistrationCode.withTransaction {status ->
      def user = lookupUserClass().findByEmail(registrationCode.username)
      user.password = springSecurityService.encodePassword(command.password, salt)
      user.save()
      registrationCode.delete()
    }

    springSecurityService.reauthenticate registrationCode.username

    flash.message = message(code: 'spring.security.ui.resetPassword.success')

    log.info("Password reset , redirecting to ${params.targetUrl}");
    redirect uri: params.targetUrl;
  }


  protected Class<?> lookupUserClass(String role) {
    if (role.equals(Role.ROLE_CUSTOMER)) {
      grailsApplication.getDomainClass("com.makkaldeals.auth.Customer").clazz
    }
    else {
      return super.lookupUserClass();
    }
  }

  protected String generateLink(String action, linkParams) {
    createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
            controller: 'register', action: action,
            params: linkParams)

  }

  protected String evaluate(s, binding) {
    new SimpleTemplateEngine().createTemplate(s).make(binding)
  }

  static final passwordValidator = {String password, command ->
    if (command.email && command.email.equals(password)) {
      return 'command.password.error.username'
    }

    if (password && password.length() >= 8 && password.length() <= 64 &&
            (!password.matches('^.*\\p{Alpha}.*$') ||
                    !password.matches('^.*\\p{Digit}.*$'))) {
      return 'command.password.error.strength'
    }
  }

  static final areaCodeValidator = {String areaCode, command ->
    if (!areaCode.isInteger()) {
      return 'command.areacode.error'
    }
  }


}

class RegisterCommand {

  String email
  String password
  //todo validate areacode
  String areaCode
  String firstName;
  String lastName;
  String businessName;
  String category;
  String address;
  String city;
  String state;
  String country;
  String phone;
  String website;


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

