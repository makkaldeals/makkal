package com.grepdeals

import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder

import com.grepdeals.auth.Role;
import com.grepdeals.auth.User
import com.grepdeals.auth.UserRole;
import com.grepdeals.consts.CategoryTree;

class UserCategory {
	
	User user
	
	String category
	
	boolean equals(other) {
		if (!(other instanceof UserCategory)) {
			return false
		}

		other.user?.id == user?.id &&
			other.category?.id == category?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (category) builder.append(category.id)
		builder.toHashCode()
	}
	
	static UserCategory create(User user, String category, boolean flush = false) {
		new UserCategory(user: user, category: category).save(flush: flush, insert: true)
	}

	static boolean remove(User user, String category, boolean flush = false) {
		UserCategory instance = UserCategory.findByUserAndCategory(user, category)
		instance ? instance.delete(flush: flush) : false
	}

	static void removeAll(User user) {
		executeUpdate 'DELETE FROM UserCategory WHERE user=:user', [user: user]
	}
	
	static Set<String> getCategories(User user) {
		UserCategory.findAllByUser(user).collect { it.user} as Set
	}


}
