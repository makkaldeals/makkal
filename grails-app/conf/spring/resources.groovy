// Place your Spring DSL code here
beans = {
  mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
    host = 'smtp.gmail.com'
    port = 465
    username = 'admin@makkaldeals.com'
    password = 'makkaladmin'
    javaMailProperties = ['mail.smtp.auth': 'true',
            'mail.smtp.socketFactory.port': '465',
            'mail.smtp.socketFactory.class': 'javax.net.ssl.SSLSocketFactory',
            'mail.smtp.socketFactory.fallback': 'false']
    }
}
