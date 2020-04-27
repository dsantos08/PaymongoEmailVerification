package com.practice.tests;

import com.coreclasses.core.API_Reusable;
import com.sampleapi.payloads.PlacesAPIPayload;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SampleAPITest_MainMethod {
    public static void main(String args[]){
        //Validate if API is working as expected

        /*
        Rest Assured works via
        GIVEN - all input details; includes the queryparam, header, body
        WHEN - submit the API; includes resource and http method
        THEN - validating the addAddressResponse
         */
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        //Add Address
        String addAddressResponse =
                given().queryParam("key","qaclick123")
                        .header("Content-Type","application/json")
                        .body(PlacesAPIPayload.AddPlace()) //present in addPlacePayload class
                        .when().post("maps/api/place/add/json")
                        .then().assertThat()
                        .statusCode(200)
                        .body("scope", equalTo("APP"))
                        .header("server", "Apache/2.4.18 (Ubuntu)")
                        .extract().response().asString(); //Saves the addAddressResponse in a String variable

        System.out.println(addAddressResponse);

        //Parsing the JSON and get the value of a specific parameter
        //JsonPath jsAddPlace = new JsonPath(addAddressResponse); //this helps parse the JSON responses and I can now get the values
        String placeId = API_Reusable.rawToJson(addAddressResponse).getString("place_id");

        //Update the Address
        String newAddress = "This is the new address";
        String updateAddressResponse =
                given().queryParam("key","qaclick123")
                        .header("Content-Type","application/json")
                        .body(PlacesAPIPayload.UpdatePlace(placeId, newAddress))
                        .when().put("maps/api/place/update/json")
                        .then().assertThat().statusCode(200)
                        .body("msg", equalTo("Address successfully updated"))
                        .extract().response().asString();

        System.out.println(updateAddressResponse);

        //Get Address and verify that value was updated
        String getAddressResponse =
                given().queryParam("key","qaclick123")
                        .queryParam("place_id", placeId)
                        .header("Content-Type","application/json")
                        .when().get("maps/api/place/get/json")
                        .then().assertThat().statusCode(200)
                        .extract().response().asString();

        //JsonPath jsGetPlace = new JsonPath(getAddressResponse); //this helps parse the JSON responses and I can now get the values
        String getPlaceValue = API_Reusable.rawToJson(getAddressResponse).get("address");

        Assert.assertEquals(getPlaceValue, newAddress);

        /*if (getPlaceValue.equals(newAddress)){
            System.out.println("Address was successfully updated with value: " + getPlaceValue);
        }
        else
        {
            System.out.println("Address was not updated with value: " + getPlaceValue);
            System.out.println("Value of address is " + newAddress);
        }*/

        //Delete the Added Address via place_id
        String deleteAddressResponse =
                given().queryParam("key","qaclick123")
                        .header("Content-Type","application/json")
                        .body(PlacesAPIPayload.DeletePlace(placeId))
                        .when().post("maps/api/place/delete/json")
                        .then().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println(deleteAddressResponse);
    }
}
