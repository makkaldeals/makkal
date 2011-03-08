import com.makkaldeals.auth.*;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH;

class BootStrap {

  def springSecurityService;

  def init = {servletContext ->
    createDefaultUsersAndRoles();
    createTestData();

  }

  def createDefaultUsersAndRoles() {

    String password = springSecurityService.encodePassword(CH.config.makkaldeals.user.admin.password);

    def roleAdmin = Role.findByAuthority(Role.ROLE_ADMIN) ?: new Role(authority: Role.ROLE_ADMIN).save();

    Role.findByAuthority(Role.ROLE_CLIENT) ?: new Role(authority: Role.ROLE_CLIENT).save();

    Role.findByAuthority(Role.ROLE_CUSTOMER) ?: new Role(authority: Role.ROLE_CUSTOMER).save();

    def admin = new User(email: CH.config.makkaldeals.user.admin.email, password: password, enabled: true).save();

    UserRole.create(admin, roleAdmin, true);
  }

  def createTestData() {

    String clientPwd = springSecurityService.encodePassword("client2011");
    String customerPwd = springSecurityService.encodePassword("customer2011");

    def customer = Customer.create(
                  email: "e.rajasekar@gmail.com",
                  password: customerPwd,
                  firstName: "Rajasekar",
                  lastName: "Gmail",
                  businessName: "customer1",
                  category: "category1",
                  address: "address1",
                  city: "Tampa",
                  state: "FL",
                  areaCode: "33634",
                  country: "United States",
                  phone: "111111111",
                  accountLocked:false);

     UserRole.create(customer, Role.findByAuthority(Role.ROLE_CUSTOMER),true);

     def client = new User(email: "e.rajasekar@makkaldeals.com" , password:clientPwd, enabled:true).save();

     UserRole.create(client, Role.findByAuthority(Role.ROLE_CLIENT),true);

     Date now = new Date();

     def post = new Post(title:"title1" , content:"content1" , dateCreated:now, lastUpdated:now);
     customer.addToPosts(post);

     log.info("RAJA ${post.title} by ${post.author.firstName}")


  }

  def destroy = {
  }
}
