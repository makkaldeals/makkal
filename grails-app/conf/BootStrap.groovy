import com.makkaldeals.auth.*;

class BootStrap {

    def springSecurityService;

    def init = { servletContext ->

	String password = springSecurityService.encodePassword('password');

	def roleAdmin = new Role(authority: 'ROLE_ADMIN').save();

	def roleUser = new Role(authority: 'ROLE_USER').save();

	def user = new User(email: 'makkaluser', password: password, enabled: true).save();

	def admin = new User(email: 'makkaladmin', password: password, enabled: true).save();

	UserRole.create user, roleUser;
	UserRole.create admin, roleUser; 
	UserRole.create admin, roleAdmin, true;

    }
    def destroy = {
    }
}
