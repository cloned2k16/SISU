package com.inditext.sisu.util;

public interface ILogger {
	public void info 	( String fmt, Object ... args);
	public void debug 	( String fmt, Object ... args);
	public void warn 	( String fmt, Object ... args);
	public void error 	( String fmt, Object ... args);

}
