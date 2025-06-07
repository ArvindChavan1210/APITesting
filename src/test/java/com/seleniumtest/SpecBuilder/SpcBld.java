package com.seleniumtest.SpecBuilder;

import com.seleniumtest.Files.googlePayload;
import com.seleniumtest.Utilities.propertiesReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SpcBld {

    @Test
    public void buildSpecs() {
        RestAssured.baseURI = propertiesReader.readData("googleAPI", "url");
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        RequestSpecification req = requestSpecBuilder.setBaseUri(propertiesReader.readData("googleAPI", "url")).
                addQueryParam("key", "qaclick123").build();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        ResponseSpecification resp = responseSpecBuilder.expectStatusCode(200).build();
        Response response = given().spec(req).body(googlePayload.addPlace()).when().post("/add/json")
                .then().spec(resp).extract().response();
        String place_id = response.jsonPath().getString("place_id");
        System.out.println(place_id);
        Response getResponse = given().spec(req).queryParam("place_id", place_id).log().all().
                when().get("/get/json").
                then().spec(resp).log().all()
                .extract().response();

        getResponse.prettyPrint();

    }

}

