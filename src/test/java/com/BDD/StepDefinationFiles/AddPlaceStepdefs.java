package com.BDD.StepDefinationFiles;

import com.BDD.BodyData.addPlaceBody;
import com.seleniumtest.Utilities.addPlaceRequestTypes;
import com.seleniumtest.Utilities.reqSpecs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class AddPlaceStepdefs {
    RequestSpecification rspc;
    Response response;
    ResponseSpecification resp;

    @Given("Add place payload")
    public void add_place_payload() throws FileNotFoundException {
        rspc = reqSpecs.requestSpecification("url").body(addPlaceBody.addPlaceData());

    }


    @Then("then api call get success with status code {int}")
    public void then_api_call_get_success_with_status_code(Integer statusCode) {
        Assert.assertEquals(response.statusCode(), statusCode.intValue(), "Unexpected status code!");
    }


    @Then("{string} in response boy is {string}")
    public void in_response_boy_is(String string, String string2) {
        String actualValue = response.jsonPath().getString(string);
        System.out.println("Actual value for '" + string + "': " + actualValue);
        Assert.assertEquals(actualValue, string2, "Mismatch in response value for: " + string);
    }

    @When("user calls {string} with {string} https request")
    public void user_calls_with_https_request(String resourceType, String requestType) throws FileNotFoundException {
        String endpoint;
        addPlaceRequestTypes apiEnum = addPlaceRequestTypes.valueOf(resourceType); // Converts "add" to enum
        if (resourceType.equalsIgnoreCase("add") && requestType.equalsIgnoreCase("post")) {
            endpoint = apiEnum.getResource();  // Gives "/add/json"
            System.out.println(endpoint);
            response = given().spec(rspc).when().post(endpoint).then().extract().response();
            System.setProperty("place_id", response.jsonPath().getString("place_id"));
        } else if (resourceType.equalsIgnoreCase("get") && requestType.equalsIgnoreCase("get")) {
            endpoint = apiEnum.getResource();
            response = given().spec(reqSpecs.requestSpecification("url")).queryParam("place_id", System.getProperty("place_id")).
                    when().get(endpoint).then().extract().response();
        } else if (resourceType.equalsIgnoreCase("update") && requestType.equalsIgnoreCase("put")) {
            endpoint = apiEnum.getResource();
            response = given().spec(rspc).queryParam("place_id", System.getProperty("place_id")).
                    when().put(endpoint).then().extract().response();
        } else if (resourceType.equalsIgnoreCase("delete") && requestType.equalsIgnoreCase("delete")) {
            endpoint = apiEnum.getResource();
            response=given().spec(rspc).when().delete(endpoint).then().extract().response();
        } else {
            System.out.println("Please Check request and response combination");
        }
    }

    @Given("Update place payload with {string};")
    public void update_place_payload_with(String address) throws FileNotFoundException {
        rspc = reqSpecs.requestSpecification("url").body(addPlaceBody.updatePlaceBody(System.getProperty("place_id"), address));

    }


    @Given("delete place payload")
    public void delete_place_payload() throws FileNotFoundException {
        rspc = reqSpecs.requestSpecification("url").body(addPlaceBody.deletePlaceBody(System.getProperty("place_id")));
    }
}
