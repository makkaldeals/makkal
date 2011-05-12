import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class SettingsController {

    static navigation = true;

	def index = { redirect action: settings }
}
