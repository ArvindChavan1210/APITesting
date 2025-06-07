package com.seleniumtest.DynamiJsonHandling;

import static io.restassured.RestAssured.given;

import java.util.Random;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.seleniumtest.Files.AddBookBody;
import com.seleniumtest.Utilities.RandomNames;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class DynamicJson {

    String url = "https://rahulshettyacademy.com/";

    @Test(dataProvider = "payload", priority = 1)
    public void addABook(String ISBN, String aisle,String name) {
        RestAssured.baseURI = url;
        Response response = given().header("Content-Type", "application/json")
                .log().all()
                .when().body(AddBookBody.bodyData("Learn Appium with Java",ISBN, aisle, name)).post("Library/Addbook.php").then().extract().response();
        Assert.assertEquals(response.jsonPath().getString("Msg"), "successfully added");
        System.out.println("----ID:"+response.jsonPath().getString("ID")+"-----");
        System.setProperty("ID", response.jsonPath().getString("ID"));

    }

    // nyk523

    @Test(priority = 4)
    public void getBookByAuthorsName() {
        RestAssured.baseURI = url;
        String response = given().header("Content-Type", "application/json").queryParam("AuthorName", "John Doe").log()
                .all().when()
                .get("Library/GetBook.php").then().extract().response().asString();
        JsonPath path = new JsonPath(response);
        System.out.println(path.getList("isbn"));
    }

    @Test(priority = 2, dependsOnMethods = "addABook")
    public void getBookByID() {
        RestAssured.baseURI = url;
        Response response = given().header("Content-Type", "application/json")
                .queryParam("ID", System.getProperty("ID"))
                .log().all().when().get("Library/GetBook.php").then().statusCode(200).extract().response();
        response.prettyPrint();
    }

    @Test(priority = 3)
    public void deleteBook() {
        RestAssured.baseURI = url;
        Response response = given().header("Content-Type", "application/json").log().all().when().body("{\r\n" + //
                "    \"ID\":\""+System.getProperty("ID")+"\"\r\n" + //
                "}").delete("Library/DeleteBook.php");
        System.out.println(response.asPrettyString());
        Assert.assertEquals("book is successfully deleted", response.jsonPath().getString("msg"));
    }

    @DataProvider(name = "payload")
    public Object[][] createData() {
        Random random = new Random();
        int num = random.nextInt(900) + 100; // ensures a 3-digit number

        Object[][] bodyData = {
                { RandomNames.randomChars(), String.valueOf(num),RandomNames.randomFullName()}
        };
        return bodyData;
    }
}
