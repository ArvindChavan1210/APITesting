package com.seleniumtest.OAuth2andDeserialization;

import com.seleniumtest.Utilities.propertiesReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GenerateTokenTest {

    String url = propertiesReader.readData("googleAPI", "oauth");
    String access_token = "";

    @Test
    public void getToken() {
        RestAssured.baseURI = url;
        Response response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
                log().all().when().post("/oauth2/resourceOwner/token").then().extract().response();
        access_token = response.jsonPath().get("access_token");
        System.out.println("Access Toekn :" + access_token);
    }

    @Test(dependsOnMethods = "getToken")
    public void getDetails(){
        baseURI=url;
        Response response=given().queryParam("access_token",access_token)
                .pathParam("details","getCourseDetails").log().all().when().get("/{details}")
                .then().extract().response();
        response.prettyPrint();
        DesrializationDetails ds=response.as(DesrializationDetails.class);
        System.out.println(ds.getCourses().getWebAutomation().get(0).getPrice());

    }

}
