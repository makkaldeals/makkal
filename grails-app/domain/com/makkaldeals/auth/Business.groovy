package com.makkaldeals.auth

/**
 * com.makkaldeals.auth
 *
 * Created on Mar 5, 2011 . 4:45:46 PM
 * @Author E. Rajasekar
 *
 */
class Business {

  String name;
  String category;
  String address;
  String city;
  String state;
  String country;
  String website;

  static constraints = {

    name blank:false, unique:true;
    category blank:false;
    address blank: false;
    city blank: false;
    state blank: false;
    country blank: false;
    website nullable:true, url:true;
  }
}
