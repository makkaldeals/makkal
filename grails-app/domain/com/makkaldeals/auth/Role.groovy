package com.makkaldeals.auth

class Role {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_CLIENT = "ROLE_CLIENT";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}

    public String toString(){
      return authority;
    }
}
