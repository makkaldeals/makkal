package com.makkaldeals.auth

import org.grails.taggable.*

/**
 * com.makkaldeals.auth
 *
 * Created on Mar 7, 2011 . 4:45:46 PM
 * @Author E. Rajasekar
 *
 */

class Post implements Taggable{

    static belongsTo = [author:Customer]

    String title;
    String content;

    Date dateCreated;
	Date lastUpdated;

    boolean published;

	static constraints = {
		title blank:false
		content blank:false
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
