package com.paymongo.tests;

import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import com.paymongopages.PaymongoActivationPage;
import com.paymongopages.PaymongoLoginPage;
import com.paymongopages.PaymongoSignupPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import com.coreclasses.guerrillamail.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PaymongoSignupTest extends Browser {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String currentTime = dtf.format(now);
    JavascriptExecutor jse;

    //Links
    String homepageLink = "https://dashboard.paymongo.com/login";

    //GuerrillaMail Value Holders
    GuerrillaMail mailService;
    String tempEmail;
    ArrayList<Email> tempEmailInbox;
    Email emailHolder;
    int emailIDHolder;
    String emailBodyHolder;

    //Test Data
    String name = "testname";
    String password = "TestTest123";
    String contactNumber = "639988192381";


    @Test()
    public void initialize() throws Exception {
        setHomePage(homepageLink); //Starting point
        setDeviceMode("chrome");
        openBrowser();
        jse = (JavascriptExecutor) Browser.getDriver();
    }

    @Test(priority = 1)
    public void loginPageCheck() {
        Log.setStoryName("Verify Login page " + currentTime);
        Log.setTestScriptName("Verify that Login page is accessible");
        Log.setTestScriptDescription("Open Login page and verify that it is loading successfully by checking some of the elements");

        PaymongoLoginPage.GeneralElements.verifyPartofURL("dashboard.paymongo.com/login");
        PaymongoLoginPage.SignInFormSection.verifyPaymongoLogoPresent();
        PaymongoLoginPage.SignInFormSection.verifySignInTextPresent();
        PaymongoLoginPage.SignInFormSection.verifyEmailBoxPresent();
        PaymongoLoginPage.SignInFormSection.verifyPasswordBoxPresent();
    }

    @Test(priority = 2)
    public void signUp() throws Exception {
        Log.setStoryName("Verify Signup " + currentTime);
        Log.setTestScriptName("Verify that Signup is working");
        Log.setTestScriptDescription("Check to see that user is able to populate the Sign-in fields and register successfully.");

        PaymongoLoginPage.SignInFormSection.clickSignUpLink();

        //Get temporary email via GuerrillaMail API
        mailService = new GuerrillaMail();
        tempEmail = mailService.getEmailAddress();

        PaymongoSignupPage.UserInformationSection.populateEmail(tempEmail);
        PaymongoSignupPage.UserInformationSection.populateName(name);
        PaymongoSignupPage.UserInformationSection.populateContact(contactNumber);
        PaymongoSignupPage.UserInformationSection.populatePassword(password);
        PaymongoSignupPage.UserInformationSection.populateConfirmPassword(password);
        PaymongoSignupPage.UserInformationSection.clickTermsAndConditions();
        PaymongoSignupPage.UserInformationSection.clickSubmit();

        PaymongoActivationPage.ActivationForm.verifyAccountActivationHeader();
        PaymongoLoginPage.GeneralElements.verifyPartofURL("dashboard.paymongo.com/activate");
    }

    @Test (priority = 3)
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
    }
}