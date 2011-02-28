import com.makkaldeals.auth.*;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH;

class BootStrap {

    def springSecurityService;

    def init = { servletContext ->
        createDefaultUsersAndRoles();
    }

    def createDefaultUsersAndRoles() {

        String password = springSecurityService.encodePassword(CH.config.makkaldeals.user.admin.password);

        def roleAdmin = Role.findByAuthority(Role.ROLE_ADMIN) ?: new Role(authority: Role.ROLE_ADMIN).save();

        Role.findByAuthority(Role.ROLE_CLIENT) ?: new Role(authority: Role.ROLE_CLIENT).save();

        Role.findByAuthority(Role.ROLE_CUSTOMER) ?: new Role(authority: Role.ROLE_CUSTOMER).save();

        def admin = new User(email:CH.config.makkaldeals.user.admin.email, password: password, enabled: true).save();

        UserRole.create admin, roleAdmin, true;
    }

    def destroy = {
    }
}
