package com.seleniumtest.JIRA_Project;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.Test;

import com.seleniumtest.Files.jiraCreateBody;
import com.seleniumtest.Utilities.propertiesReader;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class createUpdate {

    String url = propertiesReader.readData("googleAPI", "jiraURL");
    String auth = propertiesReader.readData("googleAPI", "jiraauth");
    private static String key = "";

    @Test
    public void createAnIssue() {
        RestAssured.baseURI = url;
        String response = given().header("Content-Type", "application/json").header("Authorization", auth)
                .pathParam("end1", "issue")
                .log().all()
                .body(jiraCreateBody.bodyData("Bug", "Getting 404 upon click on Login Button",
                        "On Login page, when user enters username and password and click on loggin button, he is getting error 404"))
                .when().post("/{end1}").then().extract().response().asString();
        System.out.println(response);
        JsonPath JsonPath = new JsonPath(response);
        key = JsonPath.getString("key");
    }

    @Test(dependsOnMethods = "createAnIssue")
    public void updateAnIssue() {
        Response response = given()
                .header("Authorization", auth)
                .header("X-Atlassian-Token", "no-check")
                .contentType("multipart/form-data")
                .multiPart("file", new File("C:/Users/ASUS/Downloads/ID_CARD.pdf"))
                .pathParam("issueKey", key)
                .log().all()
                .when()
                .post("https://arvindchavan1210.atlassian.net/rest/api/2/issue/{issueKey}/attachments")
                .then()
                .extract().response();

        // Print pretty JSON only
        System.out.println(response.prettyPrint());

    }

}
