package com.grepdeals;

import static org.junit.Assert.*
import grails.test.GrailsUnitTestCase

import com.grepdeals.auth.User
import com.grepdeals.consts.CategoryTree

class UserCategoryTest extends GrailsUnitTestCase {

	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testWithBlankUser() {
		mockForConstraintsTests(UserCategory)
		def userCategory = new UserCategory(user:"", category: CategoryTree.AutoGlassServices )
		assertFalse userCategory.validate()
		assertEquals 1, userCategory.errors.errorCount
		assertEquals "nullable", userCategory.errors["user"]
	}
	

	void testWithBlankUserCategory() {
		mockDomain(User)
		mockForConstraintsTests(UserCategory)
		def userCategory = new UserCategory(user:User, category: "" )
		assertFalse userCategory.validate()
		assertEquals 1, userCategory.errors.errorCount
		assertEquals "nullable", userCategory.errors["category"]
	}

	void testWithBlankParameter() {
		mockForConstraintsTests(UserCategory)
		def userCategory = new UserCategory(user:"", category: "" )
		assertFalse userCategory.validate()
		assertEquals 2, userCategory.errors.errorCount
		assertEquals "nullable", userCategory.errors["user"]
		assertEquals "nullable", userCategory.errors["category"]
	}

	void testCreate() {
		def admin = new User(email: "riazmohideen@grepdeals.com", password: "justpass")
		
		mockDomain(User , [admin])
		
		def adminCategory = new UserCategory(admin, CategoryTree.AutoGlassServices)
		
		assertEquals "riazmohideen@grepdeals.com", adminCategory.getUser().email
	}
}
