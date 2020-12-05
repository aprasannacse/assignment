package com.rabo.pims.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DataUtils {

	
	public static String setExceptionTrace(Exception e) {
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); 
		return sStackTrace;
	}
}
