import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH;

beans = {
  mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
    host = 'smtp.gmail.com'
    port = 465
    username = CH.config.makkaldeals.user.admin.email
    password = CH.config.makkaldeals.user.admin.password
    javaMailProperties = ['mail.smtp.auth': 'true',
            'mail.smtp.socketFactory.port': '465',
            'mail.smtp.socketFactory.class': 'javax.net.ssl.SSLSocketFactory',
            'mail.smtp.socketFactory.fallback': 'false']
    }
}
