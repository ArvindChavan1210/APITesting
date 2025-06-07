package com.BDD.StepDefinationFiles;

import com.BDD.BodyData.addPlaceBody;
import com.seleniumtest.Utilities.addPlaceRequestTypes;
import com.seleniumtest.Utilities.reqSpecs;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class Hook {

    @BeforeStep
    public void getPlaceID(Scenario scenario) throws FileNotFoundException {
        Collection<String> tags = scenario.getSourceTagNames();
        if (tags.contains("@hook_update") || tags.contains("@hook_delete")) {
            if (System.getProperty("place_id") == null) {
                addPlaceRequestTypes apiEnum = addPlaceRequestTypes.valueOf("add"); // Converts "add" to enum
                String endPoint = apiEnum.getResource();
                Response response = given().spec(reqSpecs.requestSpecification("url")).body(addPlaceBody.addPlaceData())
                        .when().post(endPoint).then().extract().response();
                System.setProperty("place_id", response.jsonPath().getString("place_id"));
            }
        } else if (tags.contains("@hook_getBookByID")) {
            
        }

    }
}
