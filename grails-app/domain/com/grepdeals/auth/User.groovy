package com.grepdeals.auth

import com.grepdeals.UserCategory;
class User {

	String email
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    String areaCode
	String facebookId
	String facebookPassword
	UserCategory userCategories;
	

	static constraints = {
		email blank: false, unique: true
		password blank: false
        areaCode nullable:true;
	}

	static mapping = {
		password column: '`password`'
        tablePerHierarchy false

	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

   
}
