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
		def userCategory = new UserCategory(user: "", category: CategoryTree.AutoGlassServices )
		assertFalse userCategory.validate()
		assertEquals 1, userCategory.errors.errorCount
		println userCategory.errors
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
		
		def adminCategory1 = new UserCategory(user: admin, category: CategoryTree.AutoGlassServices)
		def adminCategory2 = new UserCategory(user: admin, category: CategoryTree.Automotive)
		
		mockDomain(UserCategory, [adminCategory1, adminCategory2])
		
		assertTrue("Should have AutoGlassServices category ", admin.categories.contains(CategoryTree.AutoGlassServices))
		
			
		assertTrue("Should have Automotive category ", admin.categories.contains(CategoryTree.Automotive))
	}
	
	void testGetCategory() {
		def admin = new User(email: "riazmohideen@grepdeals.com", password: "justpass")
		
		mockDomain(User , [admin])
		
		def adminCategory1 = new UserCategory(user: admin, category: CategoryTree.AutoGlassServices)
		def adminCategory2 = new UserCategory(user: admin, category: CategoryTree.Automotive)
		
		mockDomain(UserCategory, [adminCategory1, adminCategory2])
		
		Set<CategoryTree> expectedCategories = new HashSet<CategoryTree> ()
		expectedCategories.add CategoryTree.AutoGlassServices
		expectedCategories.add CategoryTree.Automotive
		
		assertEquals expectedCategories, UserCategory.getCategories(admin)
	}
	
}
