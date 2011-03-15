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

  static hasMany = [posts: Post];

  static constraints = {
    firstName blank: false;
    phone blank: false;
    business nullable: false;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(firstName);
    sb.append(" , " + business?.name);
    sb.append(" , " + phone);

  }

}
