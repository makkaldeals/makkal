package com.grepdeals.auth


/**
 * com.grepdeals.auth
 *
 * Created on May 19, 2011 . 11:10:46 PM
 * @Author Riaz
 *
 */
class UserService {

  def springSecurityService;
  static transactional = true

  public User update(Map params) {
	System.out.println(params.id);
	//TODO how to get the user object to update?
	//System.out.println(params.Assigned-Categories);
	//for(String category : params.Assigned-Categories) {
	//	System.out.println(category)
	//}
  }
}
