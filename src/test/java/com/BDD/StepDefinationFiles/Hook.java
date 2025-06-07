package com.BDD.StepDefinationFiles;

import com.BDD.BodyData.addPlaceBody;
import com.seleniumtest.Utilities.addPlaceRequestTypes;
import com.seleniumtest.Utilities.reqSpecs;
import io.cucumber.java.BeforeStep;
import io.restassured.response.Response;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class Hook {

    @BeforeStep
    public void getPlaceID() throws FileNotFoundException {
        if (System.getProperty("place_id") == null) {
            addPlaceRequestTypes apiEnum = addPlaceRequestTypes.valueOf("add"); // Converts "add" to enum
            String endPoint=apiEnum.getResource();
            Response response = given().spec(reqSpecs.requestSpecification()).body(addPlaceBody.addPlaceData())
                    .when().post(endPoint).then().extract().response();
            System.setProperty("place_id",response.jsonPath().getString("place_id"));
        }
    }
}
