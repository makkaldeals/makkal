package com.grepdeals.auth

import com.grepdeals.UserCategory
import com.grepdeals.consts.CategoryTree
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

	static constraints = {
		email blank: false, unique: true
		password blank: false
        areaCode nullable:true
		facebookId nullable:true
		facebookPassword nullable:true;
	}

	static mapping = {
		password column: '`password`'
        tablePerHierarchy false

	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	void addCategory(CategoryTree category) {
		UserCategory.create(this.email, category, true)
	}
	
	Set<CategoryTree> getCategories() {
		return UserCategory.getCategories(this.email)
	}
	
	void removeCategory(CategoryTree category) {
		UserCategory.remove(this.email, category, true)
	}
   
	void removeAllCategories() {
		UserCategory.removeAll(this.email)
	}
	
}
