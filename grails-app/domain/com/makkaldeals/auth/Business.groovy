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
  //TODO: areaCode is duplicated in User & business, find better way to deal with it.
  String areaCode;
  String website;

  static constraints = {

    name blank:false;
    category blank:false;
    address blank: false;
    city blank: false;
    state blank: false;
    areaCode blank:false;
    country blank: false;
    website nullable:true, url:true;
  }
}
