package com.makkaldeals.auth

class Customer extends User {

  String firstName;
  String lastName;
  String businessName;
  String category;
  String address;
  String city;
  String state;
  String country;
  String phone;
  String website;

    static constraints = {
      firstName blank:false;
      address blank:false;
      city blank:false;
      state blank:false;
      country blank:false
      phone blank:false;
    }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(firstName);
      sb.append(" , " + businessName);
      sb.append(" , " + address);
      sb.append(" , " + phone);

    }
}
