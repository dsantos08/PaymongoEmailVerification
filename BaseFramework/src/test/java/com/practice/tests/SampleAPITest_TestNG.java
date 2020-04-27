package com.practice.tests;

import com.coreclasses.core.API_Reusable;
import com.coreclasses.core.Browser;
import com.coreclasses.core.Log;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.sampleapi.payloads.PlacesAPIPayload;
import io.restassured.RestAssured;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SampleAPITest_TestNG extends Browser {
    String addAddressResponse, placeId, newAddress, updateAddressResponse, getPlaceValue, getAddressResponse;
    String deleteAddressResponse;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String currentTime = dtf.format(now);

    @Test()
    public void initialize() throws Exception {
        setHomePage("https://google.com"); //Starting point
        setDeviceMode("chrome");
        openBrowser();
    }

    @Test (priority = 1)
    public void test1() {
        Log.setStoryName("Test Story Name " + currentTime);
        Log.setTestScriptName("Test Script Name");
        Log.setTestScriptDescription("Test Description");

        try {
            RestAssured.baseURI = "https://rahulshettyacademy.com";
            //Add Address
            addAddressResponse =
                    given().queryParam("key", "qaclick123")
                            .header("Content-Type", "application/json")
                            .body(PlacesAPIPayload.AddPlace()) //present in addPlacePayload class
                            .when().post("maps/api/place/add/json")
                            .then().assertThat()
                            .statusCode(200)
                            .body("scope", equalTo("APP"))
                            .header("server", "Apache/2.4.18 (Ubuntu)")
                            .extract().response().asString(); //Saves the addAddressResponse in a String variable
            //System.out.println(addAddressResponse);
            //Log.setLog(addAddressResponse);
            Log.testStep("PASSED", addAddressResponse, "Request successful");
        }
        catch (AssertionError e){
            Log.testStep("FAILED", e.toString(), "Request successful");
        }
    }

    @Test (priority = 2)
    public void test2(){
        //Parsing the JSON and get the value of a specific parameter
        //JsonPath jsAddPlace = new JsonPath(addAddressResponse); //this helps parse the JSON responses and I can now get the values
        placeId = API_Reusable.rawToJson(addAddressResponse).getString("place_id");

        //Update the Address
        newAddress = "This is the new address";
        updateAddressResponse =
                given().queryParam("key","qaclick123")
                        .header("Content-Type","application/json")
                        .body(PlacesAPIPayload.UpdatePlace(placeId, newAddress))
                        .when().put("maps/api/place/update/json")
                        .then().assertThat().statusCode(200)
                        .body("msg", equalTo("Address successfully updated"))
                        .extract().response().asString();

        //System.out.println(updateAddressResponse);
        Log.setLog(updateAddressResponse);
    }

    @Test (priority = 3)
    public void test3(){
        //Get Address and verify that value was updated
        getAddressResponse =
                given().queryParam("key","qaclick123")
                        .queryParam("place_id", placeId)
                        .header("Content-Type","application/json")
                        .when().get("maps/api/place/get/json")
                        .then().assertThat().statusCode(200)
                        .extract().response().asString();

        //JsonPath jsGetPlace = new JsonPath(getAddressResponse); //this helps parse the JSON responses and I can now get the values
        getPlaceValue = API_Reusable.rawToJson(getAddressResponse).get("address");

        Assert.assertEquals(getPlaceValue, newAddress);
        //System.out.println(getAddressResponse);
        Log.setLog(getAddressResponse);

        /*if (getPlaceValue.equals(newAddress)){
            System.out.println("Address was successfully updated with value: " + getPlaceValue);
        }
        else
        {
            System.out.println("Address was not updated with value: " + getPlaceValue);
            System.out.println("Value of address is " + newAddress);
        }*/
    }

    @Test (priority = 4)
    public void test4(){
        //Delete the Added Address via place_id
        deleteAddressResponse =
                given().queryParam("key","qaclick123")
                        .header("Content-Type","application/json")
                        .body(PlacesAPIPayload.DeletePlace(placeId))
                        .when().post("maps/api/place/delete/json")
                        .then().assertThat().statusCode(999)
                        .extract().response().asString();

        // System.out.println(deleteAddressResponse);
        Log.setLog(deleteAddressResponse);
    }
}
