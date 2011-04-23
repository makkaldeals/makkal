package com.grepdeals

import com.grepdeals.auth.User;

class UserCategory {
	
	static belongsTo = [author:User]
	
	String categories;
}
