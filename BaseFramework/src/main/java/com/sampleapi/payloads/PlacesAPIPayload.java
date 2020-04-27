package com.sampleapi.payloads;

public class PlacesAPIPayload {
    public static String AddPlace(){
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"TestDom\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://testdom.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n";
    }
    public static String UpdatePlace(String placeId, String addresValue){
        return "{\n" +
                "\"place_id\":" + "\"" + placeId + "\"" + ",\n" +
                "\"address\":" + "\"" + addresValue + "\"" + ",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}\n";
    }
    public static String DeletePlace(String placeId){
        return "{\n" +
                "    \"place_id\":" + "\"" + placeId + "\"" + "\n" +
                "}\n";
    }

}
