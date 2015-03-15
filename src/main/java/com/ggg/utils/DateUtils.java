package com.ggg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static long referenceTime;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static void setStart() {
		referenceTime = System.currentTimeMillis();
	}
	
	public static long getDuration() throws Error {
		if(referenceTime == 0) throw new Error("setStart not previously called!"); 
		return System.currentTimeMillis() - referenceTime;
	}
	
	public static String getCurrentDateTime() {
		return format.format(new Date());
	}

}
