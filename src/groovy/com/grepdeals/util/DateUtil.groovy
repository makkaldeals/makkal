package com.grepdeals.util

import java.text.SimpleDateFormat
import java.sql.Timestamp
import org.codehaus.groovy.grails.commons.ConfigurationHolder  as CH;

/**
 * Created by IntelliJ IDEA.
 * User: arthi
 * Date: 5/19/11
 * Time: 12:19 AM
 * To change this template use File | Settings | File Templates.
 */
final class DateUtil {

  private static final SimpleDateFormat JQUERY_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

  public static Date getToday() {
    return setMidnight(new Date())
  }

  public static Date getTomorrow() {
    return (getToday() + 1) as Date
  }

  public static Date setMidnight(Date theDate) {
    Calendar cal = Calendar.getInstance()
    cal.setTime(theDate)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    cal.getTime()
  }

  public static String convertToJqueryFormat(Date date) {
    return date ? JQUERY_DATE_FORMAT.format(date) : null;
  }

  public static String convertTimestampToJqueryFormat(Timestamp timestamp) {
    if (timestamp) {
      Calendar cal = new GregorianCalendar();
      cal.setTimeInMillis(((java.sql.Timestamp) timestamp).getTime());
      return JQUERY_DATE_FORMAT.format(cal.getTime());
    } else {
      return null;
    }
  }

  public static Date convertFromJqueryFormat(String dateString){
     return JQUERY_DATE_FORMAT.parse(dateString);
  }

  public static String getDefaultExpiresOn(){
     Date today = getToday();
     return convertToJqueryFormat(today + CH.config.grepdeals.posts.expiresOn.defaultDurationInDays);
  }
}
