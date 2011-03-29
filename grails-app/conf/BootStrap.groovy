import com.grepdeals.auth.*;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import com.grepdeals.Post
import com.grepdeals.Business;
import grails.util.Environment;

class BootStrap {

  def springSecurityService;

  def init = {servletContext ->
    createDefaultUsersAndRoles();
    createTestData();

  }

  def createDefaultUsersAndRoles() {

    String password = springSecurityService.encodePassword(CH.config.grepdeals.user.admin.password);

    def roleAdmin = Role.findByAuthority(Role.ROLE_ADMIN) ?: new Role(authority: Role.ROLE_ADMIN).save();

      Role.findByAuthority(Role.ROLE_CLIENT) ?: new Role(authority: Role.ROLE_CLIENT).save();

      Role.findByAuthority(Role.ROLE_CUSTOMER) ?: new Role(authority: Role.ROLE_CUSTOMER).save();

    def admin = new User(email: CH.config.grepdeals.user.admin.email, password: password, enabled: true).save();

    UserRole.create(admin, roleAdmin, true);
  }

  def createTestData() {

    // Lets not create test data in production

    if (!Environment.current.equals(Environment.PRODUCTION)) {
      
      def clientRole = Role.findByAuthority(Role.ROLE_CLIENT)
      def customerRole = Role.findByAuthority(Role.ROLE_CUSTOMER)
      String password = springSecurityService.encodePassword("grepdeals");

      def u1 = User.build(email: 'client1@grepdeals.com', password: password, enabled: true, accountLocked: false, areaCode: "33634");
      UserRole.create(u1, clientRole, true);

      def b1 = Business.build();
      def b2 = Business.build(name: b1.name, category: b1.category, city: 'Miami', areaCode: "45678");// same name as b1, but different city,areacode
      def b3 = Business.build(name: b1.name, category: b1.category, areaCode: "33635");
      Map customers = [:];

      customers.c0 = Customer.build(email: "e.rajasekar@grepdeals.com", business: b1, password: password);
      customers.c1 = Customer.build(business: b1, password: password);
      customers.c2 = Customer.build(business: b1, password: password);
      customers.c3 = Customer.build(business: b2, password: password);
      customers.c4 = Customer.build(business: b3, password: password);
      def p11 = Post.build(author: customers.c1);
      def p12 = Post.build(author: customers.c1);
      def p21 = Post.build(author: customers.c2);
      def p31 = Post.build(author: customers.c3);
      def p41 = Post.build(author: customers.c4);
      p11.parseTags("a");
      p12.parseTags("a");
      p21.parseTags("b");
      p31.parseTags("b");
      p41.parseTags("c")
      customers.values().each {  c ->
        UserRole.create(c, customerRole, true);
      }
    }


  }

  def destroy = {
  }
}
