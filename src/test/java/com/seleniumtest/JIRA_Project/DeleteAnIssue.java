package com.seleniumtest.JIRA_Project;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.seleniumtest.Utilities.propertiesReader;

import io.restassured.RestAssured;

public class DeleteAnIssue {

     String url=propertiesReader.readData("googleAPI", "jiraURL");
    String auth=propertiesReader.readData("googleAPI", "jiraauth");

    @Test
    public void delteIssue(){

        RestAssured.baseURI=url;
        given().header("Content-Type","application/json").header("Authorization", auth).
        pathParam("key", "SCRUM-4").log().all().when().delete("/issue/{key}").then().assertThat().statusCode(204);
        
    }
}
