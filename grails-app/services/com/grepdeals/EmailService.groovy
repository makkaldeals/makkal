package com.grepdeals


import groovy.text.SimpleTemplateEngine

import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import com.grepdeals.auth.User
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.springframework.beans.factory.InitializingBean
import grails.util.GrailsUtil;
import grails.util.Environment;


/**
 * com.grepdeals
 *
 * Created on Mar 12, 2011 . 07:04:46 PM
 * @Author E. Rajasekar
 *
 */
class EmailService implements InitializingBean{

  static transactional = false
  def mailService;
  def conf;
  def facebookGraphService;

  void afterPropertiesSet() {
    Class configClass;
    GroovyClassLoader classLoader = new GroovyClassLoader(EmailService.classLoader)
        try {
            configClass = classLoader.loadClass('EmailServiceConfig')
        } catch (ClassNotFoundException ex) {
            log.error( "EmailServiceConfig.groovy not found");
        }
    ConfigSlurper configSlurper = new ConfigSlurper(GrailsUtil.environment);
    conf = configSlurper.parse(configClass);
  }

  public void sendPostConfirmation(Post post) {
    def body = conf.email.postConfirmation.body;
    def email = post.author.email;
    String url = generateLink('posts' , 'showPost', [id : post.id])
    if (body.contains('$')) {
      body = evaluate(body, [user:post.author, url: url])
    }
    if(facebookGraphService != null)
    {
        facebookGraphService.publishWall(post.getTitle())
    }
    mailService.sendMail {
      to email
      from conf.email.from
      subject conf.email.postConfirmation.subject
      html body.toString()
    }
  }
  
  public void sendAdvertisementToUsers(Post post, List<String> userEmails) {
	  def body = conf.email.advertisement.body;
	  String url = generateLink('posts' , 'showPost', [id : post.id])
	  if (body.contains('$')) {
		body = evaluate(body, [post:post, url: url])
	  }
	  mailService.sendMail {
		to "admin@grepdeals.com"
		bcc userEmails
		from conf.email.from
		subject conf.email.advertisement.subject
		html body.toString()
	  }
	}

  public void sendVerifyRegistration(String email, String role, String targetUrl){
    def registrationCode = new RegistrationCode(username: email).save()
    String url = generateLink('register' ,'verifyRegistration', [t: registrationCode.token, role: role, targetUrl: targetUrl])

    def body = conf.email.register.body

    if (body.contains('$')) {
      body = evaluate(body, [url: url])
    }
    mailService.sendMail {
      to email
      from conf.email.from
      subject conf.email.register.subject
      html body.toString()
    }
  }

  public void sendApproval(User user, String role, String targetUrl) {
   String url = generateLink('register' , 'approveRegistration', [role: role, email: user.email, targetUrl: targetUrl])

    def body = conf.email.approve.body
    if (body.contains('$')) {
      body = evaluate(body, [user: user, url: url])
    }
    mailService.sendMail {
      to CH.config.grepdeals.user.admin.email
      from conf.email.from
      subject conf.email.approve.subject
      html body.toString()
    }
  }

  public void sendForgotPassword(String email, String targetUrl){
    def registrationCode = new RegistrationCode(username: email).save()
    String url = generateLink('register' , 'resetPassword', [t: registrationCode.token, targetUrl: targetUrl])

    def body = conf.email.forgotPassword.body
    if (body.contains('$')) {
      body = evaluate(body, [url: url])
    }

    mailService.sendMail {
      to email
      from conf.email.from
      subject conf.email.forgotPassword.subject
      html body.toString()
    }
  }
  
  private String evaluate(s, binding) {
   new SimpleTemplateEngine().createTemplate(s).make(binding)
  }

  private String generateLink(String controller, String action, linkParams) {
    def request = RequestContextHolder.getRequestAttributes().getRequest();
    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()

		if(Environment.current.equals(Environment.PRODUCTION)) {
			g.createLink(base: "$request.scheme://www.grepdeals.com",
            controller: controller, action: action,
            params: linkParams)
		}else {
			g.createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
			controller: controller, action: action,
			params: linkParams)
		}
  }

    public void contactUs(String firstName, String lastName, String email, String phoneNumber, String reasonToContact) {

        def body = conf.email.contactUs.body
        if (body.contains('$')) {
            body = evaluate(body, [firstName: firstName, lastName: lastName, email: email, phoneNumber: phoneNumber, reasonToContact: reasonToContact])
        }
        mailService.sendMail {
            to CH.config.grepdeals.user.admin.email
            from conf.email.from
            subject conf.email.contactUs.subject
            html body.toString()
        }
    }
}
