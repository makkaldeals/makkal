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

    def customer1 = Customer.create(
                  email: "e.rajasekar@gmail.com",
                  password: customerPwd,
                  firstName: "Rajasekar",
                  lastName: "Gmail",
                  businessName: "business1",
                  category: "category1",
                  address: "address1",
                  city: "Tampa",
                  state: "FL",
                  areaCode: "33634",
                  country: "United States",
                  phone: "111111111",
                  accountLocked:false);

     UserRole.create(customer1, Role.findByAuthority(Role.ROLE_CUSTOMER),true);

     def customer2 = Customer.create(
                  email: "customer2@makkaldeals.com",
                  password: customerPwd,
                  firstName: "Customer 2",
                  lastName: "Gmail",
                  businessName: "business1",
                  category: "category2",
                  address: "address1",
                  city: "Miami",
                  state: "FL",
                  areaCode: "43634",
                  country: "United States",
                  phone: "22222222",
                  accountLocked:false);

     UserRole.create(customer2, Role.findByAuthority(Role.ROLE_CUSTOMER),true);

     def customer3 = Customer.create(
                  email: "customer3@makkaldeals.com",
                  password: customerPwd,
                  firstName: "Customer 3",
                  lastName: "Gmail",
                  businessName: "business2",
                  category: "category2",
                  address: "address1",
                  city: "Tampa",
                  state: "FL",
                  areaCode: "33634",
                  country: "United States",
                  phone: "22222222",
                  accountLocked:false);

     UserRole.create(customer3, Role.findByAuthority(Role.ROLE_CUSTOMER),true);

      def customer4 = Customer.create(
                  email: "customer4@makkaldeals.com",
                  password: customerPwd,
                  firstName: "Customer 4",
                  lastName: "Gmail",
                  businessName: "business1",
                  category: "category2",
                  address: "address1",
                  city: "Tampa",
                  state: "FL",
                  areaCode: "33635",
                  country: "United States",
                  phone: "22222222",
                  accountLocked:false);

     UserRole.create(customer4, Role.findByAuthority(Role.ROLE_CUSTOMER),true);

     def client = new User(email: "e.rajasekar@makkaldeals.com" , password:clientPwd, enabled:true).save();

     UserRole.create(client, Role.findByAuthority(Role.ROLE_CLIENT),true);

     Date now = new Date();

     def post = new Post(title:"title1" , content:"content1" , dateCreated:now, lastUpdated:now, published:true);
     post.author = customer2;
     post.save();

  }

  def destroy = {
  }
}
