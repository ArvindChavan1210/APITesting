package com.seleniumtest.JIRA_Project;

import org.testng.annotations.Test;

import com.seleniumtest.Utilities.propertiesReader;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;

public class getIssue {

    String url=propertiesReader.readData("googleAPI", "jiraURL");
    String auth=propertiesReader.readData("googleAPI", "jiraauth");

    @Test
    public void getJIRAIssue(){
        RestAssured.baseURI=url;
        given().header("Content-Type","application/json").header("Authorization", auth).
        pathParam("end1", "issue").pathParam("end2", "SCRUM-10")
        .log().all().when().get("/{end1}/{end2}").then().extract().response().prettyPrint();
    }

}
