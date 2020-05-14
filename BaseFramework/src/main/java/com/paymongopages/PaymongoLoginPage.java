package com.paymongopages;

import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import com.coreclasses.webelements.Button;
import com.coreclasses.webelements.Element;
import com.coreclasses.webelements.Link;
import com.coreclasses.webelements.TextBox;
import org.openqa.selenium.By;

public class PaymongoLoginPage {

    public static class SignInFormSection {
        private static Element _paymongoLogo = new Element("Paymongo Logo", By.xpath("//div[@class='equiac-logo']//img"));
        private static Element _signinText = new Element("Sign In Text", By.xpath("//span[contains(text(),'Sign in to your Account')]"));
        private static TextBox _emailBox = new TextBox("Email Address Input",By.xpath("//input[@placeholder='E-mail Address']"));
        private static TextBox _passwordBox = new TextBox("Password Input",By.xpath("//input[@placeholder='Password']"));
        private static Link _forgotPasswordLink = new Link("Forgot Password", By.xpath("//a[@class='forgot-password-link']"));
        private static Link _signUpLink = new Link("Signup Text", By.xpath("//a[@class='sign-up']"));
        private static Button _signInButton = new Button("Sign In", By.xpath("//button[@class='ant-btn login-form-button button ant-btn-primary']"));

        public static void verifyPaymongoLogoPresent(){
            _paymongoLogo.verifyDisplayed();
        }

        public static void verifySignInTextPresent(){
            _signinText.verifyDisplayed();
        }

        public static void verifyEmailBoxPresent(){
            _emailBox.verifyDisplayed();
        }

        public static void verifyPasswordBoxPresent(){
            _passwordBox.verifyDisplayed();
        }

        public static void populateEmail(String i){
            _emailBox.setText(i);
        }

        public static void populatePassword(String i){
            _passwordBox.setText(i);
        }

        public static void clickForgotPassword(){
            _forgotPasswordLink.click();
        }

        public static void clickSignUpLink(){
            _signUpLink.click();
        }

        public static void clickSignIn(){
            _signInButton.click();
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
