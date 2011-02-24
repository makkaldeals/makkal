package com.makkaldeals.auth

class User {

	String email
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    String areaCode

	static constraints = {
		email blank: false, unique: true
		//password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
}
