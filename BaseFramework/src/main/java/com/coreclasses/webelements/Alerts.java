package com.coreclasses.webelements;

import com.coreclasses.core.Browser;

public class Alerts extends Browser {

	public static void acceptAlert() {
		getDriver().switchTo().alert().accept();
	}

}
