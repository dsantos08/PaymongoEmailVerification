package com.coreclasses.core;

import io.restassured.path.json.JsonPath;

public class API_Reusable extends Browser {

        public static JsonPath rawToJson(String jsonString){
            JsonPath js = new JsonPath(jsonString);
            return js;
        }

        public static void verifyStatusCode(String code, String expectedCode){
            if (code.equals(expectedCode)){
                Log.testStep("PASSED", "Response code " + code + " is equal to expected " + expectedCode, "Response code " + code + " is equal to expected " + expectedCode);
            }
            else {
                Log.testStep("FAILED", "Response code " + code + " is NOT equal to expected " + expectedCode, "Response code " + code + " is equal to expected " + expectedCode);;
            }
        }
}
