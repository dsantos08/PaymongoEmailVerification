package com.paymongo.tests;

import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import com.coreclasses.reusable.RandomString;
import com.paymongopages.PaymongoActivationSteps;
import com.paymongopages.PaymongoLoginPage;
import com.paymongopages.PaymongoSignupPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymongoSignUp_HappyPath extends Browser {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String currentTime = dtf.format(now);
    JavascriptExecutor jse;

    //Links
    String stagingHomepageLink = "https://dashboard.staging.paymongo.dev/login";

    //Mail Value Holders
    String individualAccount;

    //Test Data
    String name = "testname";
    String lastname = "testlastname";
    String password = "Password123";
    String contactNumber = "639988192381";

    @Test()
    public void initialize() throws Exception {
        setHomePage(stagingHomepageLink); //Starting point
        setDeviceMode("chrome");
        openBrowser();
        jse = (JavascriptExecutor) Browser.getDriver();
    }

    //@Test(priority = 1)
    public void signUp_happyPath() throws Exception {
        Log.setStoryName("Verify Signup - Happy Path " + currentTime);
        Log.setTestScriptName("Verify that creating a new account is working");
        Log.setTestScriptDescription("Check to see that user is able to populate the Sign-in fields and register successfully.");

        PaymongoLoginPage.SignInFormSection.clickSignUpLink();

        //Generate randomized email for use in sign-up with @doms.paymongo.net as the domain
        individualAccount = (RandomString.getAlphaNumericString(10) + "@doms.paymongo.net");

        PaymongoSignupPage.UserInformationSection.populateEmail(individualAccount);
        PaymongoSignupPage.UserInformationSection.populateFirstName(name);
        PaymongoSignupPage.UserInformationSection.populateLastName(lastname);
        PaymongoSignupPage.UserInformationSection.populateContact(contactNumber);
        PaymongoSignupPage.UserInformationSection.populatePassword(password);
        PaymongoSignupPage.UserInformationSection.populateConfirmPassword(password);
        PaymongoSignupPage.UserInformationSection.clickTermsAndConditions();

        PaymongoSignupPage.UserInformationSection.clickSubmit();
        Thread.sleep(5000);
        PaymongoLoginPage.GeneralElements.verifyPartofURL("dashboard.paymongo.com/activate");
    }

    @Test (priority = 2)
    public void verifyKYC_happyPath() throws Exception{
        Log.setStoryName("Verify KYC 2.0 - Happy Path " + currentTime);
        Log.setTestScriptName("Verify KYC steps are working");
        Log.setTestScriptDescription("This goes through the ideal scenario of completing the KYC");

        //Log.setLog("Manually confirm the email from gmail. 60 seconds wait time set.");
        //Thread.sleep(60000);

        //Temporary fix is to just log-in
        PaymongoLoginPage.SignInFormSection.populateEmail("WJktjlnECV@doms.paymongo.net");
        PaymongoLoginPage.SignInFormSection.populatePassword("Password123");
        PaymongoLoginPage.SignInFormSection.clickSignIn();

        //getDriver().get("https://dashboard.paymongo.com/activate"); comment out for now to just log-in instead of signup
        PaymongoActivationSteps.GeneralElements.verifyAccountActivationHeader();

        //Populate KYC Step 1 - Individual
        PaymongoActivationSteps.KYCStep1.populateDatePicker("2002-05-14");
        PaymongoActivationSteps.KYCStep1.populateAddressFirstLine("Address Line 1");
        PaymongoActivationSteps.KYCStep1.populateAddressSecondLine("Address Line 2");
        PaymongoActivationSteps.KYCStep1.populateCity("City Value");
        PaymongoActivationSteps.KYCStep1.populateProvince("Albay");
        PaymongoActivationSteps.KYCStep1.populateZipCode("999");
        PaymongoActivationSteps.KYCStep1.idDocsUpload(System.getProperty("user.dir") + "/testfiles/TestPDF.pdf");
        Log.setLog("Wait for 10 seconds to ensure that upload is completed.");
        Thread.sleep(10000);
        PaymongoActivationSteps.KYCStep1.clickContinue();

        //Populate KYC Step 2 - Individual

    }

    /*@Test (priority = 3)
    public void verifyEmail() throws Exception {
        Log.setStoryName("Verify account verification via Email " + currentTime);
        Log.setTestScriptName("Verify that Email verification is working");
        Log.setTestScriptDescription("After registration; user receives the account verification email. After opening it the activation page should be displayed.");

        Log.setLog("Allow some time for the sending of the confirmation email. This is completely out of the automation's control and therefore will be handled via hard coded sleep.");
        Thread.sleep(45000);

        tempEmailInbox = mailService.getEmailList();

        //From the list of emails received; select the confirmation email and open the link
        //Get the mail id of the response with subject: "Confirm the e-mail address of your PayMongo account"
        for (int counter = 0; counter < tempEmailInbox.size(); counter++) {
            if (tempEmailInbox.get(counter).getSender().equalsIgnoreCase("support@paymongo.com")){
                emailIDHolder = tempEmailInbox.get(counter).getId();
                emailHolder = mailService.fetchEmail(emailIDHolder);
            }
        }

        //Fetch the specific mail body for the confirmation email
        //Cut the link into an accessible format via browser
        emailBodyHolder = emailHolder.getBody();
        String confirmationLink = emailBodyHolder.split("\"")[1].replace(";","&");

        //Go to the confirmation link
        getDriver().get(confirmationLink);

        Thread.sleep(3000);
        getDriver().get("https://dashboard.paymongo.com/activate");
        PaymongoActivationPage.SideBar.verifyActivatePresent();

        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();

        PaymongoActivationPage.ActivationForm.verifyAccountActivationHeader();
        PaymongoActivationPage.ActivationForm.verifyAccountActivateInstructions();
        PaymongoActivationPage.ActivationForm.verifyBusinessInformationSection();
        PaymongoActivationPage.ActivationForm.verifyBankInformationSection();
    }*/
}