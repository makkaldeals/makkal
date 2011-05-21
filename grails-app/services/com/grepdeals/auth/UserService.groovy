package com.grepdeals.auth

import com.grepdeals.auth.User
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

  public void update(Map params) {
	//def  user1 = springSecurityService.currentUser
	//System.out.println(params.id);
		//TODO how to get the user object to update?
	//System.out.println(params.Assigned-Categories);
	//for(String category : params.Assigned-Categories) {
	//	System.out.println(category)
	//}
  }
}
