package com.makkaldeals

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import groovy.text.SimpleTemplateEngine
import com.makkaldeals.auth.Post
import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import com.makkaldeals.auth.User
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.springframework.beans.factory.InitializingBean;

/**
 * com.makkaldeals
 *
 * Created on Mar 12, 2011 . 07:04:46 PM
 * @Author E. Rajasekar
 *
 */
class EmailService implements InitializingBean{

  static transactional = true
  def mailService;
  def conf;

  void afterPropertiesSet() {
    conf = SpringSecurityUtils.securityConfig;
  }

  public void sendPostConfirmation(Post post) {
    def body = conf.ui.postConfirmation.emailBody;
    def email = post.author.email;
    String url = generateLink('posts' , 'showPost', [id : post.id])
    if (body.contains('$')) {
      body = evaluate(body, [user:post.author, url: url])
    }

    mailService.sendMail {
      to email
      from conf.ui.postConfirmation.emailFrom
      subject conf.ui.postConfirmation.emailSubject
      html body.toString()
    }
  }

  public void sendVerifyRegistration(String email, String role, String targetUrl){
    def registrationCode = new RegistrationCode(username: email).save()
    String url = generateLink('register' ,'verifyRegistration', [t: registrationCode.token, role: role, targetUrl: targetUrl])

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
  }

  public void sendApproval(User user, String role, String targetUrl) {
   String url = generateLink('register' , 'approveRegistration', [role: role, email: user.email, targetUrl: targetUrl])

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
  }

  public void sendForgotPassword(String email, String targetUrl){
    def registrationCode = new RegistrationCode(username: email).save()
    String url = generateLink('register' , 'resetPassword', [t: registrationCode.token, targetUrl: targetUrl])

    def body = conf.ui.forgotPassword.emailBody
    if (body.contains('$')) {
      body = evaluate(body, [url: url])
    }

    mailService.sendMail {
      to email
      from conf.ui.forgotPassword.emailFrom
      subject conf.ui.forgotPassword.emailSubject
      html body.toString()
    }
  }
  
  private String evaluate(s, binding) {
   new SimpleTemplateEngine().createTemplate(s).make(binding)
  }

  private String generateLink(String controller, String action, linkParams) {
    def request = RequestContextHolder.getRequestAttributes().getRequest();
    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()

    g.createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
            controller: controller, action: action,
            params: linkParams)

  }


}
