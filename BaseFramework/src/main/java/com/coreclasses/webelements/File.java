package com.coreclasses.webelements;

import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class File extends Browser {

	private By by;
	private WebElement element;
	private String name;

	JavascriptExecutor jse;

	public File(String name, By by) {
		this.by = by;
		this.name = name;
	}

	public void upload(String filePath) {
		element = Browser.findElement(by);
		jse = (JavascriptExecutor) Browser.getDriver();
		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 5);
		if (element == null) {
			Log.testStep("FAILED", name + " is NOT displayed", name + " is displayed");
		} else {
			jse.executeScript("arguments[0].style.display = 'block';", element);
			element.sendKeys(filePath);
			Log.testStep("PASSED", name + " has been uploaded", name + " has been uploaded");
		}
	}
}