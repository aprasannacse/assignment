package com.rabo.pims.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jManager {
	

private static Logger objDebug = LogManager.getLogger("DEBUG_LOGGER");
private static Logger objError = LogManager.getLogger("ERROR_LOGGER");

public static void writeDebugLog(String msg){
	objDebug.debug(msg);   
}
public static void writeErrorLog(String msg){
	objError.error(msg);
}


}