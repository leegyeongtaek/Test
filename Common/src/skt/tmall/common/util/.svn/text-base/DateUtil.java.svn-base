package skt.tmall.common.util;

import java.util.Calendar;

public class DateUtil {

	/**
	 * SYSDATE를 RETURN한다. 
	 * @param dateSpilt	(날짜 SPILIT)
	 * @param timeSplit	(시간 SPILIT)
	 * @return
	 */
	public static String dateTime(String dateSpilt, String timeSplit) {
		
		Calendar cal = Calendar.getInstance();
		String dateString, timeString;
		
		dateString = String.format("%04d"+dateSpilt+"%02d"+dateSpilt+"%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY)+1, cal.get(Calendar.DAY_OF_MONTH));
		timeString = String.format("%02d"+timeSplit+"%02d"+timeSplit+"%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		
		return dateString + " " + timeString;
	}
	
}
