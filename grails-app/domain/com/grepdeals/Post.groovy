package com.grepdeals

import org.grails.taggable.*
import com.grepdeals.auth.Customer

/**
 * com.grepdeals.auth
 *
 * Created on Mar 7, 2011 . 4:45:46 PM
 * @Author E. Rajasekar
 *
 */

class Post implements Taggable{

    static belongsTo = [author:Customer]
    public static final int SUBJECT_MAX_SIZE = 1500;

    String title;
    String subject;
    String content;
    
    Date dateCreated;
	Date lastUpdated;
    Date expiresOn;

    boolean published;

	static constraints = {
		title blank:false;
        subject (blank:false, maxSize: SUBJECT_MAX_SIZE);
    	content blank:false;
        expiresOn (nullable:true);
    }
	static mapping = {
		content type:"text"
		cache true
	}

    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append(title);
      sb.append("\n-------------------------------------------------------\n");
      sb.append(content);
      sb.append("\n\n");
      return sb.toString();

    }
}
