package com.seleniumtest.googelAddPlaceAPI;

import static io.restassured.RestAssured.given;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.seleniumtest.Files.googlePayload;
import com.seleniumtest.Utilities.propertiesReader;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class updatePlace {

    @Test(priority = 2)
    public void updateAddress() {
        try {
            String placeId = System.getProperty("place_ID");
            System.out.println("Place ID: " + placeId + " **************************");

            RestAssured.baseURI = propertiesReader.readData("googleAPI", "url");

            Response response = given()
                    .header("Content-Type", "application/json")
                    .queryParam("place_id", placeId)
                    .queryParam("key", "qaclick123")
                    .log().all()
                    .body(googlePayload.updatePlacePayload(placeId))
                    .when()
                    .post("/update/json") // make sure this endpoint is correct
                    .then()
                    .assertThat().statusCode(200)
                    .extract().response();

            System.out.println("Response:\n" + response);

            Assert.assertEquals(response.body().jsonPath().get("msg").toString(), "Address successfully updated");

        } catch (Exception e) {
            System.out.println("Error during update:");
            e.printStackTrace();
        }
    }

}
