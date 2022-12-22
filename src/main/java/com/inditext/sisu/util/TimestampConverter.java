package com.inditext.sisu.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimestampConverter {
	
	private static ILogger log = Logger.getLogger(TimestampConverter.class);

	private static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd-HH.mm.ss"; //"2020-06-15-12.30.00"
	
	/**
	 * 
	 * @param onDate
	 * 			an sql Date to convert
	 * @return
	 * 			date formatted in a 'standard' way
	 */
	public static String format(Date onDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);  
		return sdf.format(onDate);
	}

	/**
	 * try to parse from known date format 
	 * to a long timestamp value
	 * 
	 */
	
	public static long parseQueryDate(String dateStr) {
		
		// very simple fall back strategy
		
		String [] knownFormats = {STANDARD_DATETIME_FORMAT 	
								 ,"yyyy-MM-dd"              
								 ,"dd MMM yyyy - HH:mm:ss"
								 ,"dd MMM yyyy" };   
		long time=-1; // default to error
		
		for (String frmt : knownFormats){
			SimpleDateFormat format = new SimpleDateFormat(frmt);
			try {
				time=	format.parse(dateStr).getTime();
				log.info("decoded datetime using %s",frmt);
				break;
			}
			catch ( ParseException pe) {
				log.debug("failed to parse date using %s",frmt);
			}
		}
		log.debug("time: %d",time);
		return time;
				
	}
	
	

}
