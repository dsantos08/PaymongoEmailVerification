package com.coreclasses.core;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Browser {

	private static String browser;
	private static String downloadFilepath = System.getProperty("user.dir").replace("\\", "/")
			+ "/src/main/resources/downloads/";
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public static String environment = "Demo";
	private static String extentConfigPath = System.getProperty("user.dir").replace("\\", "/")
			+ "/src/main/resources/Config/extent-config.xml";
	private static String extentReportPath = System.getProperty("user.dir").replace("\\", "/") + "/target/extentReport/"
			+ environment + "_ExtentReport.html";
	private static ThreadLocal<ExtentTest> logger = new ThreadLocal<ExtentTest>();
	private static ExtentReports report;
	private static Integer searchLoop = 1;
	private static String seleniumGridUrl = System.getProperty("webdriver.base.url");
	private static String homePage;
	private static String deviceMode;

	public static void setHomePage(String i){
		homePage = i;
	}

	public static void setDeviceMode(String i){
		browser = i;
	}

	public static void delay(int seconds) {
		try {
			Thread.sleep(seconds * 5);
		} catch (InterruptedException e) {
		}
	}

	public static WebElement findElement(By by) throws WebDriverException {
		WebElement element = null;
		int i = 0;
		int attempts = 0;
		waitForBrowserToLoadCompletely(getDriver());
		while (i != searchLoop && element == null) {
			i += 1;
			try {
				element = new WebDriverWait(getDriver(), 10).ignoring(StaleElementReferenceException.class)
						.until(ExpectedConditions.presenceOfElementLocated(by));
			} catch (Exception e) {
				element = null;
			}
		}
		if (element != null) {
			try {
				while (attempts < 2) {
					try {
						Actions actions = new Actions(getDriver());
						actions.moveToElement(element);
						actions.perform();
					} catch (StaleElementReferenceException e) {
					}
					attempts++;
				}
			} catch (Exception e) {
			}
		}
		return element;
	}

	public static List<WebElement> findElements(By by) throws WebDriverException {
		List<WebElement> elements = null;
		int i = 0;
		int attempts = 0;
		while (i != searchLoop && elements.size() == 0) {
			i += 1;
			try {
				elements = new WebDriverWait(getDriver(), 10).ignoring(StaleElementReferenceException.class)
						.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			} catch (Exception e) {
				elements = null;
			}
		}
		if (elements != null) {
			try {
				while (attempts < 2) {
					try {
						Actions actions = new Actions(getDriver());
						actions.moveToElement(elements.get(0));
						actions.perform();
					} catch (StaleElementReferenceException e) {
					}
					attempts++;
				}
			} catch (Exception e) {
			}
		}
		return elements;
	}

	public static List<WebElement> findElements(By parentBy, By childBy) throws WebDriverException {
		WebElement element = null;
		List<WebElement> elements = null;
		int i = 0;
		int attempts = 0;
		while (i != searchLoop && elements.size() == 0) {
			i += 1;
			try {
				element = new WebDriverWait(getDriver(), 10).ignoring(StaleElementReferenceException.class)
						.until(ExpectedConditions.presenceOfElementLocated(parentBy));
				elements = element.findElements(childBy);
			} catch (Exception e) {
				elements = null;
			}
		}
		if (elements != null) {
			try {
				while (attempts < 2) {
					try {
						Actions actions = new Actions(getDriver());
						actions.moveToElement(elements.get(0));
						actions.perform();
					} catch (StaleElementReferenceException e) {
					}
					attempts++;
				}
			} catch (Exception e) {
			}
		}
		return elements;
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static ExtentTest getLogger() {
		return logger.get();
	}

	public static int getTabCount() {
		return new ArrayList<String>(getDriver().getWindowHandles()).size();
	}

	public static void navigateBackward() throws IOException {
		getDriver().navigate().back();
		Log.testStep("PASSED", "Navigate Backward", "Navigate Backward");
	}

	protected static void navigateForward() throws IOException {
		getDriver().navigate().forward();
		Log.testStep("PASSED", "Navigate Forward", "Navigate Forward");
	}

	public static void openBrowser() throws Exception {
		if (browser.equalsIgnoreCase("firefox")) {
			// Set Path for the executable file
			System.setProperty("webdriver.gecko.driver", "src/main/resources/Drivers/geckodriver.exe");
			System.setProperty("webdriver.firefox.marionette", "true");

			DesiredCapabilities cap = DesiredCapabilities.firefox();

			driver.set(new FirefoxDriver(cap));
			getDriver().manage().window().maximize();

		} else if (browser.equalsIgnoreCase("ie")) {
			// Set Path for the executable file
			System.setProperty("webdriver.ie.driver", "src/main/resources/Drivers/IEDriverServer.exe");
			driver.set(new InternetExplorerDriver());
			getDriver().manage().window().maximize();

		} else if (browser.equalsIgnoreCase("htmlUnit")) {
			HtmlUnitDriver driver = new HtmlUnitDriver();
			driver.setJavascriptEnabled(true);

		} else if (browser.equalsIgnoreCase("chrome")) {
			String driverType = null;
			if (OSChecker.isWindows()) {
				driverType = "chromedriver.exe";
			} else if (OSChecker.isMac()) {
				driverType = "chromedriver_mac";
			}

			System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/" + driverType);
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("prefs", chromePrefs);
			chromeOptions.addArguments("start-maximized");
			chromeOptions.addArguments("mute-audio");
			chromeOptions.addArguments("disable-extensions");
			//chromeOptions.addArguments("headless");
			chromeOptions.addArguments("disable-gpu");
			chromeOptions.addArguments("window-size=1600x1200");
			chromeOptions.addArguments("allow-running-insecure-content");

			DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
			chromeCapabilities.setJavascriptEnabled(true);
			chromeCapabilities.setAcceptInsecureCerts(true);
			chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			driver.set(new ChromeDriver(chromeCapabilities));

		} else if (browser.equalsIgnoreCase("mobile")) {
			String driverType = null;
			if (OSChecker.isWindows()) {
				driverType = "chromedriver.exe";
			} else if (OSChecker.isMac()) {
				driverType = "chromedriver_mac";
			}

			System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/" + driverType);
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.setBinary("C:\\Users\\Dom's Laptop\\AppData\\Local\\Chromium\\Application\\chrome.exe");
			chromeOptions.setExperimentalOption("prefs", chromePrefs);
			chromeOptions.addArguments("start-maximized");
			chromeOptions.addArguments("mute-audio");
			chromeOptions.addArguments("disable-extensions");
			chromeOptions.addArguments("headless");
			chromeOptions.addArguments("disable-gpu");
			chromeOptions.addArguments("window-size=1800x1300");
			chromeOptions.addArguments("allow-running-insecure-content");

			//Mobile Emulation
			Map<String, String> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceName", "iPhone X");
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

			DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
			chromeCapabilities.setJavascriptEnabled(true);
			chromeCapabilities.setAcceptInsecureCerts(true);
			chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			driver.set(new ChromeDriver(chromeCapabilities));

		} else if (browser.equalsIgnoreCase("grid_chrome")) {
			ChromeOptions gridChromeOptions = new ChromeOptions();
			gridChromeOptions.addArguments("start-maximized");
			gridChromeOptions.addArguments("mute-audio");
			gridChromeOptions.addArguments("disable-extensions");
			gridChromeOptions.addArguments("headless");
			gridChromeOptions.addArguments("disable-gpu");
			gridChromeOptions.addArguments("window-size=1600x1200");

			DesiredCapabilities gridChromeCapabilities = DesiredCapabilities.chrome();
			gridChromeCapabilities.setJavascriptEnabled(true);
			gridChromeCapabilities.setAcceptInsecureCerts(true);
			gridChromeCapabilities.setCapability(ChromeOptions.CAPABILITY, gridChromeOptions);

			driver.set(new RemoteWebDriver(new URL(seleniumGridUrl), gridChromeCapabilities));

		} else if (browser.equalsIgnoreCase("grid_ie")) {
			DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
			capability.setCapability("nativeEvents", false);
			capability.setCapability("unexpectedAlertBehaviour", "accept");
			capability.setCapability("ignoreProtectedModeSettings", true);
			capability.setCapability("disable-popup-blocking", true);
			capability.setCapability("enablePersistentHover", true);
			driver.set(new RemoteWebDriver(new URL(seleniumGridUrl), capability));
			getDriver().manage().window().maximize();

		} else if (browser.equalsIgnoreCase("grid_firefox")) {
			DesiredCapabilities capability = DesiredCapabilities.firefox();
			driver.set(new RemoteWebDriver(new URL(seleniumGridUrl), capability));
			getDriver().manage().window().maximize();

		} else {
			throw new IllegalArgumentException("Could not find supported browser: " + browser);
		}
		Log.setLog("Start automation test in " + browser.replace("grid_", "") + " with Dimensions: "
				+ getDriver().manage().window().getSize());
	}

	public static void refreshPage() throws IOException {
		getDriver().navigate().refresh();
		Log.testStep("PASSED", "Page is Refreshed", "Page is Refreshed");
	}

	public static void switchWindowTab(int tab) throws InterruptedException {
		try {
			int loop = 0;
			int tabCount = 0;
			while (tabCount != (tab + 1) && loop != 5) {
				tabCount = getTabCount();
				delay(5);
				loop += 1;
			}
			ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
			getDriver().switchTo().window(tabs.get(tab));
		} catch (Exception e) {
			Log.testStep("FAILED", "Tab/Window is NOT available", "Tab/Window is available");
			throw e;
		}
	}

	public static void waitForBrowserToLoadCompletely(WebDriver driver) {
		String state = null;
		String oldstate = null;
		try {
			int i = 0;
			while (i < 5) {
				Thread.sleep(1000);
				state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
				if (state.equals("interactive") || state.equals("loading"))
					break;
				if (i == 1 && state.equals("complete")) {
					//Log.setLog("Browser loading complete");
					return;
				}
				i++;
			}
			i = 0;
			oldstate = null;
			Thread.sleep(1000);
			while (true) {
				state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
				if (state.equals("complete"))
					break;
				if (state.equals(oldstate))
					i++;
				else
					i = 0;
				if (i == 15 && state.equals("loading")) {
					driver.navigate().refresh();
					i = 0;
				} else if (i == 6 && state.equals("interactive")) {
					return;
				}
				Thread.sleep(1000);
				oldstate = state;
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) throws Exception {
		logger.set(report.	startTest((this.getClass().getSimpleName() + " :: " + method.getName()), method.getName()));
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws Exception {
		if (ITestResult.FAILURE == result.getStatus()) {
			Screenshot.capture("Failed_" + result.getName() + "_");
			getLogger().log(LogStatus.FAIL, result.getThrowable());
		} else if (ITestResult.SUCCESS == result.getStatus()) {
			Screenshot.capture("Passed_" + result.getName() + "_");
		}
		//getDriver().quit();
		getDriver().get(homePage);
		Log.setLog(browser.replace("grid_", "") + " instance closed.");

		report.endTest(getLogger());
		report.flush();
	}

	@AfterTest
	public void afterTest(){
		getDriver().quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		report.close();
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws IOException {
		report = new ExtentReports(extentReportPath, true);
		report.loadConfig(new File(extentConfigPath));
		report.addSystemInfo("Environment", environment);
	}

}
