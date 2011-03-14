package com.makkaldeals.auth

/**
 * com.makkaldeals.auth
 *
 * Created on Mar 13, 2011 . 11:10:46 PM
 * @Author E. Rajasekar
 *
 */
class CustomerService {

    static transactional = true

    public Customer create(Map params) {

    Business business = Business.findByNameAndAreaCode(params.businessName, params.areaCode);

    /*TODO: Need to handle case when user enter different address for same business name.
    In that case we need to confirm with user .   */

    //Look up existing business record before inserting
    if (!business) {
      business = new Business(name: params.businessName, category: params.category, address: params.address, city: params.city, state: params.state, areaCode: params.areaCode, country: params.country, website: params.website);
      if (!business.save()) {
        log.error("Error in saving business ${business.errors}");
      }
    }
    else {
      log.warn("Adding customer to exisiting business ${business.name} found with area code ${business.areaCode}")
    }


    Customer customer = new Customer(email: params.email,
            password: params.password,
            firstName: params.firstName,
            lastName: params.lastName,
            business: business,
            areaCode: params.areaCode,
            phone: params.phone,
            accountLocked: (params.accountLocked != null) ? params.accountLocked : true,
            enabled: true);
    if (!customer.save()) {
      log.error("Error in saving user ${customer.errors}");
    }

    return customer;

  }
}
