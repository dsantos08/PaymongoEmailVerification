package com.coreclasses.core;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.Reporter;

public class Log extends Browser {

	private static Logger consoleLog = Logger.getLogger(Log.class.getName());

	public static void setLog(String name) {
		Reporter.log("[Log:] " + name);
		consoleLog.info("[Log:] " + name);
		getLogger().log(LogStatus.INFO, "[Log:] " + name);
	}

	public static void setScreenshot(String name) {
		Reporter.log("[Screenshot:] " + name);
		consoleLog.info("[Screenshot:] " + name);
		getLogger().log(LogStatus.INFO, "Snapshot below: " + getLogger().addScreenCapture(name));
	}

	public static void setStoryName(String name) {
		Reporter.log("[Story Name] " + name);
		consoleLog.info("[Story Name] " + name);
		getLogger().log(LogStatus.INFO, "[Story Name] " + name);
	}

	public static void setTestCaseNote(String name) {
		Reporter.log("[Note:] " + name);
		consoleLog.info("[Note:] " + name);
		getLogger().log(LogStatus.INFO, "[Note:] " + name);
	}

	public static void setTestScriptDescription(String name) {
		Reporter.log("[Test Script Description] " + name);
		consoleLog.info("[Test Script Description] " + name);
		getLogger().log(LogStatus.INFO, "[Test Script Description] " + name);
	}

	public static void setTestScriptName(String name) {
		Reporter.log("[Test Script Name] " + name);
		consoleLog.info("[Test Script Name] " + name);
		getLogger().log(LogStatus.INFO, "[Test Script Name] " + name);
	}

	public static void setTestStep(String name) {
		Reporter.log("[Test Step:] " + name);
		consoleLog.info("[Test Step:] " + name);
		getLogger().log(LogStatus.INFO, "[Test Step:] " + name);
	}

	public static void setWarning(String name) {
		Reporter.log("[Log:] " + name);
		consoleLog.warn("[Log:] " + name);
		getLogger().log(LogStatus.WARNING, "[Log:] " + name);
	}

	public static void testStep(String tag, String Actual, String Expected) {

		if (tag.toUpperCase() == "PASSED") {
			Reporter.log("[" + tag + "] " + Actual);
			consoleLog.info("[" + tag + "] " + Actual);
			getLogger().log(LogStatus.PASS, "[" + tag + "] " + Actual);
		} else if (tag.toUpperCase() == "FAILED") {
			Reporter.log("[" + tag + "] Expected: " + Expected);
			Reporter.log("[" + tag + "] Actual: " + Actual);
			consoleLog.warn("[" + tag + "] Expected: " + Expected);
			consoleLog.warn("[" + tag + "] Actual: " + Actual);
			getLogger().log(LogStatus.INFO, "[" + tag + "] Expected: " + Expected);
			getLogger().log(LogStatus.FAIL, "[" + tag + "] Actual: " + Actual);
			Assert.fail(Actual);
		} else if (tag.toUpperCase() == "WARNING") {
			Reporter.log("[" + tag + "] " + Actual);
			consoleLog.warn("[" + tag + "] " + Actual);
			getLogger().log(LogStatus.WARNING, "[" + tag + "] " + Actual);
		} else {
			Reporter.log("[" + tag + "] " + Actual);
			consoleLog.info("[" + tag + "] " + Actual);
			getLogger().log(LogStatus.UNKNOWN, "[" + tag + "] " + Actual);
		}
	}

}
