package com.grepdeals.auth

import com.grepdeals.consts.CategoryTree;

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
	user1.oldPassword=params.oldPassword
	user1.password=params.newPassword
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
}
