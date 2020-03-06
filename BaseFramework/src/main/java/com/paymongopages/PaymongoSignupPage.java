package com.paymongopages;

import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import com.coreclasses.webelements.*;
import org.openqa.selenium.By;

import javax.xml.soap.Text;

public class PaymongoSignupPage {

    public static class UserInformationSection {
        private static TextBox _emailBox = new TextBox("Email Address Input",By.xpath("//input[@placeholder='juandelacruz@gmail.com']"));
        private static TextBox _nameBox = new TextBox("Name Input",By.xpath("//input[@placeholder='Juan Dela Cruz']"));
        private static TextBox _contactBox = new TextBox("Contact Number Input", By.xpath("//input[@placeholder='+63 900 123 4567']"));
        private static TextBox _passwordInput = new TextBox("Password Input", By.xpath("//div[@class='form-row-item left']//input[@class='ant-input ant-input-lg InputComponent']"));
        private static TextBox _confirmPasswordInput = new TextBox("Confirm Password Input", By.xpath("//div[@class='form-row-item right']//input[@class='ant-input ant-input-lg InputComponent']"));
        private static CheckBox _termsAndConditions = new CheckBox("Terms and Conditions Checkbox", By.xpath("//input[@class='ant-checkbox-input']"));
        private static Button _submit = new Button("Submit Sign-up Form", By.xpath("//button[@class='ant-btn submit-button ant-btn-primary']"));

        public static void populateEmail(String i){
            _emailBox.setText(i);
        }

        public static void populateName(String i){
            _nameBox.setText(i);
        }

        public static void populateContact(String i){
            _contactBox.setText(i);
        }

        public static void populatePassword(String i){
            _passwordInput.setText(i);
        }

        public static void populateConfirmPassword(String i){
            _confirmPasswordInput.setText(i);
        }

        public static void clickTermsAndConditions(){
            _termsAndConditions.tick(true);
        }

        public static void clickSubmit(){
            _submit.click();
        }
    }

    public static class GeneralElements{
        private static String _currentURL;

        public static void verifyPartofURL(String url) {
            _currentURL = Browser.getDriver().getCurrentUrl().trim();
            if (_currentURL.contains(url)){
                Log.testStep("PASSED", "Current URL is " + _currentURL, "Expected URL is " + url);
            }
            else{
                Log.testStep("FAILED", "Current URL is " + _currentURL, "Expected URL is " + url);
            }
        }
    }

}
