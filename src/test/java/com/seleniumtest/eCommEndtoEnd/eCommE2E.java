package com.seleniumtest.eCommEndtoEnd;

import com.seleniumtest.Utilities.propertiesReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class eCommE2E {
    RequestSpecification req;
    String userID;

    @BeforeClass
    public void getAccessToken() {
        RestAssured.baseURI = propertiesReader.readData("googleAPI", "ecomm");
        Response response = given().header("Content-Type", "application/json").body("{\n" +
                "    \"userEmail\": \"arvindchavan7@gmail.com\",\n" +
                "    \"userPassword\": \"Indore@123\"\n" +
                "}").when().post("/auth/login").then().extract().response();
        response.prettyPrint();
        userID = response.jsonPath().getString("userId");
        String token = response.jsonPath().getString("token");
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(propertiesReader.readData("googleAPI", "ecomm"))
                .addHeader("Authorization", token);
        req = requestSpecBuilder.build();

    }

    @Test
    public void createProduct() {
        Response response = given().spec(req).multiPart("productName", "qwerty")
                .multiPart("productAddedBy", "657fd26d9fd99c85e8eccc95")
                .multiPart("productCategory", "fashion")
                .multiPart("productSubCategory", "shirts")
                .multiPart("productPrice", "11500")
                .multiPart("productDescription", "Addidas Originals")
                .multiPart("productFor", "women").multiPart("productImage",new File("C:/Users/ASUS/Downloads/millet-6588551__340.jpg")).
                when().post("product/add-product").then()
                .log().all()
                .extract().response();

        // Print response if needed
        System.out.println("Response Code: " + response.getStatusCode());
    }


    @Test
    public void getOrderByID() {
        Response response = given().spec(req).queryParam("id", "6837362581a20695304eb4e7").
                when().get("order/get-orders-details").then().extract().response();
        response.prettyPrint();

    }
}
