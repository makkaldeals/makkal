package com.grepdeals.auth

import java.util.List;

import com.grepdeals.UserCategory
import com.grepdeals.consts.CategoryTree

/**
 * com.grepdeals.auth
 *
 * Created on May 19, 2011 . 11:10:46 PM
 * @Author Riaz
 *
 */
class UserService {

  def springSecurityService
  
  
  static transactional = true

  public User update(Map params) {
	def  user1 = springSecurityService.currentUser
	user1.oldPassword = springSecurityService.encodePassword(params.oldPassword);
	user1.password=springSecurityService.encodePassword(params.newPassword);
	//user1.facebookSessionId = params.facebookSessionId

	user1.removeAllCategories()
	def categories = params.AssignedCategories
	if(categories instanceof java.lang.String) {
		user1.addCategory(CategoryTree.valueOf(categories))
	} else {
		categories.each { category ->
			System.out.println("Adding this Category : "+category);
			user1.addCategory(CategoryTree.valueOf(category))
		}
	}
	
	user1.save()

	log.info(user1.errors)
	def updatedUser = User.get(user1.id)
	return updatedUser
  }
  
  public List<User> getUsers(String category) {
	  //TODO we should use the join and get the users for the category
	 Set<UserCategory> userCategories = UserCategory.findAllByCategory(CategoryTree.Category.valueOf(category.trim()))
	 List<User> users = new ArrayList<User> ();
	 for (UserCategory userCategory: userCategories) {
		 users.add(userCategory.getUser())
	 }
	 
	 return users

  }
  
  public List<User> getAllUsers() {
	  
	  return User.getAll();
   }
}
