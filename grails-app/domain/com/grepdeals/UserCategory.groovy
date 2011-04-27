package com.grepdeals

import java.io.Serializable

import org.apache.commons.lang.builder.HashCodeBuilder

import com.grepdeals.consts.CategoryTree

class UserCategory implements Serializable {
	
	String email
	
	CategoryTree category
	
	boolean equals(other) {
		if (!(other instanceof UserCategory)) {
			return false
		}

		other.email?.id == email?.id &&
			other.category?.id == category?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (email) builder.append(email.id)
		if (category) builder.append(category.id)
		builder.toHashCode()
	}
	
	static UserCategory create(String email, CategoryTree category, boolean flush = false) {
		 new UserCategory(email: email, category: category.toString()).save(flush: flush, insert: true)
	}

	static boolean remove(String email, CategoryTree category, boolean flush = false) {
		UserCategory instance = UserCategory.findByEmailAndCategory(email, category)
		instance ? instance.delete(flush: flush) : false
	}

	static void removeAll(String email) {
		executeUpdate 'DELETE FROM UserCategory WHERE email=:email', [email: email]
	}
	
	static Set<CategoryTree> getCategories(String email) {
		UserCategory.findAllByEmail(email).collect { it.category} as Set
	}

	static mapping = {
		id composite: ['category', 'email']
		version false
	}

}
