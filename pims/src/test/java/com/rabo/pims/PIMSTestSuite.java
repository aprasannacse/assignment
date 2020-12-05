package com.rabo.pims;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
@RunWith(JUnitPlatform.class)
@SelectPackages("com.rabo.pims.controller")
@IncludePackages("com.rabo.pims.controller")
public class PIMSTestSuite {
	
	

}
