import com.makkaldeals.auth.*;

class BootStrap {

    private static final String ADMIN_USER_PASSWORD="gangoffive";
    def springSecurityService;

    def init = { servletContext ->
        createDefaultUsersAndRoles();
    }

    def createDefaultUsersAndRoles() {

        String password = springSecurityService.encodePassword(ADMIN_USER_PASSWORD);

        def roleAdmin = Role.findByAuthority(Role.ROLE_ADMIN) ?: new Role(authority: Role.ROLE_ADMIN).save();

        Role.findByAuthority(Role.ROLE_CLIENT) ?: new Role(authority: Role.ROLE_CLIENT).save();

        Role.findByAuthority(Role.ROLE_CUSTOMER) ?: new Role(authority: Role.ROLE_CUSTOMER).save();

        def admin = new User(email: 'mdadmin@gmail.com', password: password, enabled: true).save();

        UserRole.create admin, roleAdmin, true;
    }

    def destroy = {
    }
}
