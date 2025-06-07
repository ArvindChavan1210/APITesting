package com.GraphQL;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class TestGraphQL {

    String firstNameREST = "";

    @Test
    public void getRest() {
        String url = "http://localhost:4000/customers";

        Response response = given()
            .header("Content-Type", "application/json") // Fixed header name
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .extract()
            .response();

        firstNameREST = response.jsonPath().getString("[0].firstName");
        System.out.println("REST FirstName: " + firstNameREST);
    }

    @Test
    public void getGraph() {
        String url = "http://localhost:5000/graphql";

        String query = "{ customers { id firstName lastName } }";
        String requestBody = "{ \"query\": \"" + query.replace("\"", "\\\"") + "\" }";

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post(url)
            .then()
            .statusCode(200)
            .extract()
            .response();

        String firstNameGraphQL = response.jsonPath().getString("data.customers[0].firstName");

        System.out.println("GraphQL FirstName: " + firstNameGraphQL);
        System.out.println("REST FirstName: " + firstNameREST);

        // Optional: Assert to compare both values
        assert firstNameGraphQL.equals(firstNameREST) : "Mismatch in first names!";
    }
}
