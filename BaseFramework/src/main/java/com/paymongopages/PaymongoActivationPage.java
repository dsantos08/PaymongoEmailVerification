package com.paymongopages;

import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import com.coreclasses.webelements.*;
import org.openqa.selenium.By;

import javax.xml.soap.Text;

public class PaymongoActivationPage {

    public static class InitialOpenPopup {
        private static Element _welcomeModalTitle = new Element("Welcome to PayMongo", By.xpath("//h1[@class='e9tr3d70 intercom-c9gwge']"));
        private static Button _closeModal = new Button("X button in Modal", By.xpath("//span[@aria-label='Close']"));
        private static Element _anywhereElse = new Element("Anywhere Else on the Page", By.xpath("//div[@class='intercom-19ksytv e207j6p2']"));

        public static void verifyWelcomeMessage(){
            _welcomeModalTitle.verifyDisplayed();
        }

        public static void closeModal(){
            _anywhereElse.click();
        }
    }

    public static class SideBar {
        private static Button _activateYourAccount = new Button("Activate Your Account", By.xpath("//span[contains(text(),'Activate your account')]"));

        public static void clickActivateYourAccount(){
            _activateYourAccount.click();
        }

        public static void verifyActivatePresent(){
            _activateYourAccount.verifyDisplayed();
        }
    }

    public static class ActivationForm{
        private static Element _accountActivationHeader = new Element("Account Activation Header", By.xpath("//li[@class='breadcrumb-item active']"));
        private static Element _accountActivationInstructions = new Element("Account Activation Instructions", By.xpath("//span[contains(text(),'Please provide the required details and documents')]"));
        private static Element _businessInformationSection = new Element("Business Information Header", By.xpath("//div[contains(text(),'Business Information')]"));
        private static ListBox _businessType = new ListBox("Select Business Type", By.xpath("//div[contains(text(),'Select Business Type')]"));
        private static TextBox _businessName = new TextBox("Name of Business", By.xpath("//input[@placeholder='The Barkery Shop']"));
        private static TextBox _paymongoUser = new TextBox("Paymongo Username or Handle", By.xpath("//input[contains(@placeholder,'e.g. unnamedorganization')]"));
        private static TextBox _businessAddress1 = new TextBox("Business Address Field 1", By.xpath("//input[@placeholder='6780 Ayala Avenue']"));
        private static TextBox _businessAddress2 = new TextBox("Business Address Field 2", By.xpath("//input[@placeholder='Brgy. Bel-Air']"));
        private static TextBox _cityField = new TextBox("City Field", By.xpath("//input[@placeholder='Makati City']"));
        private static TextBox _provinceField = new TextBox("Province Field",By.xpath("//input[@placeholder='Makati City']"));
        private static TextBox _zipCode = new TextBox("Zip Code", By.xpath("//input[@placeholder='1226']"));
        private static ListBox _country = new ListBox("Country", By.xpath("//div[contains(text(),'Select Country')]"));
        private static ListBox _businessCategory = new ListBox("Business Category", By.xpath("//div[contains(text(),'Select Business Category')]"));
        private static TextBox _natureOfBusiness = new TextBox("Nature of Business", By.xpath("//textarea[@placeholder='What products and/or services do you offer? What type of customers do you usually have? How do you currently accept payments?']"));
        private static TextBox _businessURL = new TextBox("Business URL", By.xpath("//input[@placeholder='https://instagram.com/the-barkery']"));
        private static Element _bankSectionTitle = new Element("Bank Information", By.xpath("//div[contains(text(),'Bank Information')]"));
        private static ListBox _selectBank = new ListBox("Select Bank", By.xpath("//div[contains(@class,'bank-select ant-select ant-select-enabled')]//div[contains(@class,'ant-select-selection__rendered')]"));
        private static ListBox _accountType = new ListBox("Account Type", By.xpath("//div[contains(text(),'Select Bank Account Type')]"));
        private static TextBox _accountNumber = new TextBox("Account Number", By.xpath("//input[contains(@placeholder,'161803398875')]"));
        private static TextBox _accountName = new TextBox("Account Name", By.xpath("//input[contains(@placeholder,'Zooey Doge')]"));

        public static void verifyAccountActivationHeader(){
            _accountActivationHeader.verifyDisplayed();
        }

        public static void verifyAccountActivateInstructions(){
            _accountActivationInstructions.verifyDisplayed();
        }

        public static void verifyBusinessInformationSection(){
            _businessType.verifyDisplayed();
            _businessName.verifyDisplayed();
            _paymongoUser.verifyDisplayed();
            _businessAddress1.verifyDisplayed();
            _businessAddress2.verifyDisplayed();
            _cityField.verifyDisplayed();
            _provinceField.verifyDisplayed();
            _zipCode.verifyDisplayed();
            _country.verifyDisplayed();
            _businessCategory.verifyDisplayed();
            _natureOfBusiness.verifyDisplayed();
            _businessURL.verifyDisplayed();
        }

        public static void verifyBankInformationSection(){
            _bankSectionTitle.verifyDisplayed();
            _selectBank.verifyDisplayed();
            _accountType.verifyDisplayed();
            _accountNumber.verifyDisplayed();
            _accountName.verifyDisplayed();
        }
    }
}
