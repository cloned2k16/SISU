package com.inditext.sisu.util;

public class 	Logger 
implements 		ILogger 
{
	
	    
	    private  org.slf4j.Logger _log;  // the actual logger 
	    
		private Logger(Class<?> clss) {
			_log = org.slf4j.LoggerFactory.getLogger(clss);
		}
		private Logger(String name) {
			_log = org.slf4j.LoggerFactory.getLogger(name);
		}

		public static ILogger getLogger(String name) {
			Logger me = new Logger(name);
			return me;
		}

		public final static ILogger getLogger(Class<?> clss) {
			Logger me = new Logger(clss);
			return me;
		}


		@Override
		public void info(String fmt, Object... args) {
			_log.info(String.format(fmt, args));	
		}

		@Override
		public void warn(String fmt, Object... args) {
			_log.warn(String.format(fmt, args));	
		}

		@Override
		public void error(String fmt, Object... args) {
			_log.error(String.format(fmt, args));	
		}

		@Override
		public void debug(String fmt, Object... args) {
			_log.debug(String.format(fmt, args));	
		}
		public static ILogger getLogger(Object ownr) {
			return getLogger(ownr.getClass());
		}

		
}
