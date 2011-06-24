package com.grepdeals.auth


import groovy.text.SimpleTemplateEngine

import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import grails.plugins.springsecurity.ui.AbstractS2UiController
import grails.validation.ValidationException
import com.grepdeals.consts.CategoryTree
import grails.converters.JSON
import grails.plugins.springsecurity.Secured



class RegisterController extends AbstractS2UiController {

    static defaultAction = 'index'

    def saltSource
    def emailService;
    def customerService;
    def jcaptchaService;
    def springSecurityService;

    def index = {

        if (!params.role || params.role == Role.ROLE_CLIENT) {
            redirect(action: 'newUser');
        } else{
            redirect(action: 'newCustomer');
        }
    }

    def newUser = {
        params.role = Role.ROLE_CLIENT;
        [command: new RegisterCommand()];
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def newCustomer = {
        params.role = Role.ROLE_CUSTOMER;
        def command = new RegisterCommand();
        command.email = springSecurityService.currentUser.email;
        command.password = springSecurityService.currentUser.password;
        [command: command];
    }

    def register = {RegisterCommand command ->

        String role = command.role;
        String targetUrl = command.targetUrl;
        log.info("Registering with role ${role} ");
        if (command.hasErrors()) {
            command.getErrors().each {
                log.info(it);
            }
            render view: 'index', model: [command: command]
            return
        }

        if (!jcaptchaService.validateResponse("image", session.id, params.response)) {
            flash.message = message(code: 'register.jcaptcha.error')
            render view: 'index', model: [command: command]
            return
        }

        def userClass = lookupUserClass();
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
                    redirect uri: targetUrl;
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

                    //backup assigned categories

                    Set<CategoryTree> categories = user.getCategories();
                    user.removeAllCategories();
                    user.delete(flush: true);
                    //create user as ROLE_CUSTOMER

                    try {
                        user = customerService.
                                create(email: command.email,
                                        password: password,
                                        firstName: command.firstName,
                                        lastName: command.lastName,
                                        businessName: command.businessName,
                                        category: command.category,
                                        subcategory: command.subcategory,
                                        address: command.address,
                                        city: command.city,
                                        state: command.state,
                                        areaCode: command.areaCode,
                                        country: command.country,
                                        phone: command.phone,
                                        website: command.website,
                                )

                        //since user was previously client, add ROLE_CLIENT
                        userRoleClass.create(user, clientRoleInstance);

                        //restore user categories

                        user.addAllCategories(categories);
                        generateApproval(user, role, params.targetUrl);
                    } catch (ValidationException ve) {
                        command.errors = ve.errors;
                        render(view: 'index', model: [command: command])
                    }

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
            log.info("Handling registration for new user ${command.email}");
            String salt = saltSource instanceof NullSaltSource ? null : command.email
            String password = springSecurityService.encodePassword(command.password, salt)

            if (role.equals(Role.ROLE_CLIENT)) {

                user = userClass.newInstance(email: command.email,
                        password: password,
                        areaCode: command.areaCode,
                        accountLocked: true,
                        enabled: true
                )
                try {
                    user.save(failOnError: true);
                    chain(action: approveRegistration, params: [email: command.email, role: role, targetUrl: targetUrl])
                } catch (ValidationException ve) {
                    log.error("Error in saving user ${user.errors}")
                    command.errors = ve.errors;
                    render(view: 'index', model: [command: command])
                }
            }
            else if (role.equals(Role.ROLE_CUSTOMER)) {

                try {
                    user = customerService.
                            create(email: command.email,
                                    password: password,
                                    firstName: command.firstName,
                                    lastName: command.lastName,
                                    businessName: command.businessName,
                                    category: command.category,
                                    subcategory: command.subcategory,
                                    address: command.address,
                                    city: command.city,
                                    state: command.state,
                                    areaCode: command.areaCode,
                                    country: command.country,
                                    phone: command.phone,
                                    website: command.website,
                            );

                    generateApproval(user, role, targetUrl);
                } catch (ValidationException ve) {
                    command.errors = ve.errors;
                    render(view: 'index', model: [command: command])
                }

            }
            else {
                log.error("Invalid role ${role}");
                render view: 'index';
            }
        }


    }

    private void generateApproval(User user, String role, String targetUrl) {

        emailService.sendApproval(user, role, targetUrl);
        render view: 'index', model: [confirmationMessage: message(code: 'spring.security.ui.approval.sent')]

    }

    def approveRegistration = {
        emailService.sendVerifyRegistration(params.email, params.role, params.targetUrl)
        render view: 'index', model: [confirmationMessage: message(code: 'spring.security.ui.register.sent', args: [params.email])]
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

        redirect(controller: 'login', action: 'authSuccess', params: params);
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
        emailService.sendForgotPassword(user.email, params.targetUrl);

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

    def ajaxGetSubcategories = {
        CategoryTree parent = CategoryTree.valueOf(params.name);
        def subcategories = parent.allChildren().collect {message(code: "user.subcategory.label.${it.name}")}
        render subcategories as JSON;

    }

    protected Class<?> lookupUserClass(String role) {
        if (role.equals(Role.ROLE_CUSTOMER)) {
            grailsApplication.getDomainClass("com.grepdeals.auth.Customer").clazz
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

        /*if (password && password.length() >= 8 && password.length() <= 64 &&
              (!password.matches('^.*\\p{Alpha}.*$') ||
                      !password.matches('^.*\\p{Digit}.*$'))) {
        return 'command.password.error.strength'
      }  */
    }

    static final areaCodeValidator = {String areaCode, command ->
        if (!areaCode?.isInteger()) {
            return 'registerCommand.areacode.error'
        }
    }


    static final customerInfoValidator = { val, command ->

        if (command.role.equals(Role.ROLE_CUSTOMER)) {
            if (val == null || val.isEmpty()) {
                return "registerCommand.property.error";
            }
        }
    }


}

class RegisterCommand {

    String email
    String password
    String areaCode
    String firstName;
    String lastName;
    String businessName;
    String category;
    String subcategory;
    String address;
    String city;
    String state;
    String country;
    String phone;
    String website;
    String role;
    String targetUrl;


    static constraints = {

        email blank: false, email: true
        password blank: false, minSize: 8, maxSize: 64, validator: RegisterController.passwordValidator
        areaCode blank: false, validator: RegisterController.areaCodeValidator
        firstName validator: RegisterController.customerInfoValidator;
        businessName validator: RegisterController.customerInfoValidator;
        category validator: RegisterController.customerInfoValidator;
        subcategory validator: RegisterController.customerInfoValidator;
        firstName validator: RegisterController.customerInfoValidator;
        address validator: RegisterController.customerInfoValidator;
        city validator: RegisterController.customerInfoValidator;
        state validator: RegisterController.customerInfoValidator;
        country validator: RegisterController.customerInfoValidator;
        phone validator: RegisterController.customerInfoValidator;
        website url: true

    }
}

class ResetPasswordCommand {
    String email
    String password

    static constraints = {
        password blank: false, minSize: 8, maxSize: 64, validator: RegisterController.passwordValidator


    }
}

