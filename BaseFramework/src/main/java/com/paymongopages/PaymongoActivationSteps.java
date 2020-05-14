package com.paymongopages;

import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import com.coreclasses.webelements.*;
import org.openqa.selenium.By;
import org.w3c.dom.Text;

public class PaymongoActivationSteps {

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

    public static class GeneralElements{
        private static Element _accountActivationHeader = new Element("Account Activation Header", By.xpath("//p[@class='activate-heading']"));
        private static Element _draftSavedNotification = new Element("Draft Saved Notification", By.xpath("//div[@class='ant-notification ant-notification-bottomRight']//span"));

        public static void verifyAccountActivationHeader(){
            _accountActivationHeader.verifyDisplayed();
        }

        public static void verifyDraftSavedNotif(){
            _draftSavedNotification.verifyDisplayed();
        }
    }

    public static class KYCStep1 {
        private static Button _datePickerBox = new Button("Date Picker Box", By.xpath("//input[@placeholder='Select date']"));
        private static TextBox _datePickerInput = new TextBox("Date Picker Input", By.xpath("//div[@class='ant-calendar-date-input-wrap']//input[@placeholder='Select date']"));
        private static File _idDocsFileUpload = new File("PDF Doc", By.xpath("//input[@type='file']"));
        private static TextBox _addressFirstLine = new TextBox("Address First Line", By.xpath("//input[@placeholder='6780 Ayala Avenue']"));
        private static TextBox _addressSecondLine = new TextBox("Address Second Line", By.xpath("//input[@placeholder='Brgy. Bel-Air']"));
        private static TextBox _city = new TextBox("City", By.xpath("//input[@placeholder='Makati']"));
        private static Element _provinceClick = new Element("Province Field", By.xpath("//div[contains(@class,'ant-select-selection__placeholder')]"));
        private static Table _provinceList = new Table("Province List", By.xpath("//ul[contains(@class,'ant-select-dropdown-menu-root ant-select-dropdown-menu-vertical')]"));
        private static TextBox _zipCode = new TextBox("Zip Code", By.xpath("//input[@placeholder='1226']"));
        private static Button _continue = new Button("Continue to business information", By.xpath("//button[contains(@class,'ant-btn next-step-button')]"));

        public static void populateDatePicker(String date){
            _datePickerBox.click();
            _datePickerInput.setText(date);
        }

        public static void populateAddressFirstLine(String value){
            _addressFirstLine.setText(value);
        }

        public static void populateAddressSecondLine(String value){
            _addressSecondLine.setText(value);
        }

        public static void populateCity(String value){
            _city.setText(value);
        }

        public static void populateProvince(String value){
            _provinceClick.click();
            _provinceList.clickOnElementViaTextFromList(value, _provinceList.getAllLiElements());
        }

        public static void populateZipCode(String value){
            _zipCode.setText(value);
        }

        public static void idDocsUpload(String path){
            _idDocsFileUpload.upload(path);
        }

        public static void clickContinue(){
            _continue.click();
        }

    }
}

