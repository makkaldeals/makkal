import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class SettingsController {

    static navigation = true;

	def index = { render view: '/settings/settings' }
}
