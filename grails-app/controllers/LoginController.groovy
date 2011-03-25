import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.savedrequest.SavedRequest

import javax.servlet.http.HttpSession

class LoginController {

  /**
   * Dependency injection for the authenticationTrustResolver.
   */
  def authenticationTrustResolver

  /**
   * Dependency injection for the springSecurityService.
   */
  def springSecurityService

  /**
   * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
   */
  def emailService;

  def index = {
    if (springSecurityService.isLoggedIn()) {
      if (params.targetUrl){
          redirect uri: params.targetUrl;
      }else{
        redirect uri : '/';
      }

    }
    else {
      redirect action: auth, params: params
    }
  }
  
  def aboutus = {
	  render view: 'aboutus'	  
  }
  
  def contactus = {
       [command: new ContactUsCommand()]
  }
  
  def howitworks = {
	  render view: 'howitworks'
  }
  
  /**
   * Show the login page.
   */
  def auth = {

    def config = SpringSecurityUtils.securityConfig

    String view = 'auth'
    String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
    HttpSession session = request.getSession(false);
    //Redirect to previously accessed url, if user tries to access a page without logging in and user is redirected to login page.
    if (session != null) {
      SavedRequest savedRequest = (SavedRequest) session.getAttribute(WebAttributes.SAVED_REQUEST);
      if (savedRequest != null) {
        params.targetUrl = savedRequest.getRedirectUrl() - "$request.scheme://$request.serverName:$request.serverPort$request.contextPath";

      }
    }

    render view: view, model: [postUrl: postUrl,
            rememberMeParameter: config.rememberMe.parameter]
  }


  def authSuccess = {
    session.user = springSecurityService.currentUser;
    redirect uri: params.targetUrl;
  }

  /**
   * Show denied page.
   */
  def denied = {
    if (springSecurityService.isLoggedIn() &&
            authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
      // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
      redirect action: full, params: params
    }
  }

  /**
   * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
   */
  def full = {
    def config = SpringSecurityUtils.securityConfig
    render view: 'auth', params: params,
            model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                    postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
  }

  /**
   * Callback after a failed login. Redirects to the auth page with a warning message.
   */
  def authfail = {

    def refererUrl = request.getHeader("Referer");
    def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
    String msg = ''
    def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]

    if (exception) {
      if (exception instanceof AccountExpiredException) {
        msg = SpringSecurityUtils.securityConfig.errors.login.expired
      }
      else if (exception instanceof CredentialsExpiredException) {
        msg = SpringSecurityUtils.securityConfig.errors.login.passwordExpired
      }
      else if (exception instanceof DisabledException) {
          msg = SpringSecurityUtils.securityConfig.errors.login.disabled
        }
        else if (exception instanceof LockedException) {
            msg = SpringSecurityUtils.securityConfig.errors.login.locked
          }
          else {
            msg = "spring.security.ui.login.fail";
          }
    }
    if (springSecurityService.isAjax(request)) {
      render([error: msg] as JSON)
    }
    else {
      flash.message = msg
      redirect url: refererUrl;
    }
  }

    def sendContactus = {  ContactUsCommand command ->

        if (command.hasErrors()) {
            render view: 'contactus', model: [command: command]
            return
        }
        emailService.contactUs(params.firstName, params.lastName,params.email,params.phoneNumber,params.reasonToContact)
        render view: 'contactus', model: [command: new ContactUsCommand(), confirmationMessage: message(code: 'spring.security.ui.contactus.sent')]

    }


}

class ContactUsCommand {


    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String reasonToContact;

    static constraints = {

        email blank: false, email: true
        firstName blank: false, maxSize: 50;
        lastName blank: false, maxSize: 50;
        phoneNumber blank: false
        reasonToContact blank: false, maxSize: 50;


    }
}