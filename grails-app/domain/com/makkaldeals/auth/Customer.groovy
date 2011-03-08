package com.makkaldeals.auth

import org.apache.commons.logging.LogFactory

/**
 * com.makkaldeals.auth
 *
 * Created on Mar 5, 2011 . 4:45:46 PM
 * @Author E. Rajasekar
 *
 */
class Customer extends User {

  private static final log = LogFactory.getLog(this)
  
  String firstName;
  String lastName;
  Business business;
  String phone;

  static hasMany = [ posts : Post];

  static constraints = {
    firstName blank: false;
    phone blank: false;
    business nullable: false;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(firstName);
    sb.append(" , " + business.name);
    sb.append(" , " + phone);

  }

  static Customer create(Map params) {

    Business business =  Business.findByName(params.businessName);

    /*TODO: Need to handle case when user enter different address for same business name.
    In that case we need to confirm with user .   */
    
    //Look up existing business record before inserting
    if (!business) {
      business = new Business(name: params.businessName, category: params.category, address: params.address, city: params.city, state: params.state, country: params.country, website: params.website);
    }

    if (!business.save()) {
      log.error("Error in saving business ${business.errors}");
    }
    Customer customer = new Customer(email: params.email,
            password: params.password,
            firstName: params.firstName,
            lastName: params.lastName,
            business: business,
            areaCode: params.areaCode,
            phone: params.phone,
            accountLocked: (params.accountLocked != null ) ? params.accountLocked :true,
            enabled: true);
    if (!customer.save()) {
      log.error("Error in saving user ${customer.errors}") ;
    }

    return customer;

  }

}
