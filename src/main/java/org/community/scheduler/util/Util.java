package org.community.scheduler.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author tudor.codrea
 *
 */
public class Util {
	
	public static Date getNowDateMinuteOffset(int minuteOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MINUTE, -1 * minuteOffset);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date getNowDatePlusSecondOffset(int secondOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.SECOND, secondOffset);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	

}
