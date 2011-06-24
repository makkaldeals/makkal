package com.grepdeals.auth

import com.grepdeals.UserCategory
import com.grepdeals.consts.CategoryTree
class User {

	String email
	String password
	String oldPassword
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    String areaCode
	String facebookSessionId
	

	static constraints = {
		email blank: false, unique: true
		password blank: false
        areaCode nullable:true
		facebookSessionId nullable:true
		oldPassword nullable:true;
	}

	static mapping = {
		password column: '`password`'
        tablePerHierarchy false

	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}


	Set<CategoryTree> getCategories() {
		return UserCategory.getCategories(this)
	}
	
	void removeCategory(CategoryTree category) {
		UserCategory.remove(this, category, true);
	}
   
	void removeAllCategories() {
		UserCategory.removeAll(this);
	}

    void addCategory(CategoryTree category){
      UserCategory.create(this,category, true);
    }

    void addAllCategories(Set<CategoryTree> categories){
        categories.each{
            addCategory(it);
        }
    }
	
	boolean hasCategory(CategoryTree category) {
		return this.getCategories().contains(category)
	}

}
