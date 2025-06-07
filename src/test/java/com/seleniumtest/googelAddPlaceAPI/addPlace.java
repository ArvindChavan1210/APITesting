package com.seleniumtest.googelAddPlaceAPI;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seleniumtest.Files.googlePayload;
import com.seleniumtest.Utilities.propertiesReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class addPlace {

    // Simple code to validate field value in body and status code
    // also validating value form header & parsing respnse

    @Test(priority = 1)
    public void addReqPlace() {
        try {
            RestAssured.baseURI = propertiesReader.readData("googleAPI", "url");
            String response = given().header("Content-Type", "application/json").queryParam("key", "qaclick123")
                    .log().all().body(googlePayload.addPlace()).when().log().all().post("/add/json").then().assertThat()
                    .statusCode(200)
                    .body("scope", equalTo("APP")).header("server", containsString("Apache")).extract().response()
                    .asString();
            System.out.println(response);
            JsonPath jsonpath = new JsonPath(response);
            System.out.println(jsonpath.getString("place_id"));
            System.setProperty("place_ID", jsonpath.getString("place_id"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void addPlaceThroughSerialization() throws JsonProcessingException {
        SeralizationDetails sd = new SeralizationDetails();
        Location lc = new Location();
        List<String> ls = Arrays.asList("shoe park", "shop", "Icecream Parlur");
        lc.setLat("37.318504");
        lc.setLng("-122.030670");
        sd.setAccuracy(50);
        sd.setAddress("2703 town center lane sunnyvale, CA 94086 US");
        sd.setLanguage("English US");
        sd.setLocation(lc);
        sd.setName("AMC");
        sd.setPhone_number("1234567981");
        sd.setTypes(ls);
        sd.setWebsite("http://google.com");
        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(sd));

        RestAssured.baseURI = propertiesReader.readData("googleAPI", "url");
        Response resp = given().header("Content-Type", "application/json").queryParam("key", "qaclick123").log().all()
                .body(om.writeValueAsString(sd)).when().post("/add/json").then().extract().response();
        resp.prettyPrint();
        String place_id = resp.jsonPath().getString("place_id");
        Response rsp = given().header("Content-Type", "application/json").queryParam("key", "qaclick123")
                .queryParam("place_id", place_id).log().all().when().get("/get/json").then().extract().response();
        JsonPath jp=new JsonPath(rsp.asString());
        Assert.assertEquals(jp.getInt("accuracy"), sd.getAccuracy());
        Assert.assertEquals(jp.getString("name"),sd.getName());

    }

}

