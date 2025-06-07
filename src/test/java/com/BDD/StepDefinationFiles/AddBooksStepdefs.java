package com.BDD.StepDefinationFiles;

import com.seleniumtest.Files.AddBookBody;
import com.seleniumtest.Utilities.RandomNames;
import com.seleniumtest.Utilities.addBooksRequestTypes;
import com.seleniumtest.Utilities.reqSpecs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class AddBooksStepdefs {

    String expectedID;
    Response response;
    String body;
    addBooksRequestTypes addBooksRequestTypes;
    String author_name;
    String rand_chars;
    int rand_num;


    @Given("body for request with {string}")
    public void body_For_Request_With(String bookName) {
        System.out.println("Preparing body for request");
        author_name = RandomNames.randomFullName();
        rand_chars = RandomNames.randomChars();
        Random rand = new Random();
        rand_num = rand.nextInt(100, 999);
        expectedID = rand_chars + String.valueOf(rand_num);
        body = AddBookBody.bodyData(bookName, rand_chars, String.valueOf(rand_num), author_name);
        System.out.println("Expected ID :" + expectedID);
        System.setProperty("expectedID", expectedID);
        System.setProperty("isbn", rand_chars);
        System.setProperty("aisle", String.valueOf(rand_num));
        System.setProperty("author_name", author_name);
        System.out.println("Body prepared existing prepare body line");

    }


    @Then("validate response {string} code is {int}")
    public void validate_Response_Code_Is(String string, int arg1) {
        System.out.println("Status code Value Assertion started");
        Assert.assertEquals(response.statusCode(), 200, "Expected: 200, Actual: " + response.statusCode());
        System.out.println("Status code Value Assertion completed successfully");

    }

    @And("validate response body contains {string} like {string}")
    public void validate_Response_Body_Contains_Like(String string1, String string2) {
        System.out.println("Value Requested :" + response.jsonPath().getString(string1));
        Assert.assertEquals(response.jsonPath().getString(string1), string2, "Different Field available than request");
    }


    @When("user hits {string} request for {string} api and {string}")
    public void user_hits_request_for_api_and(String httpType, String url_requested, String for_get) throws FileNotFoundException {
        System.out.println("User requested " + httpType + ": Method with" + url_requested);
        addBooksRequestTypes = com.seleniumtest.Utilities.addBooksRequestTypes.valueOf(httpType.toLowerCase());
        String endPoints = addBooksRequestTypes.getRequest();
        System.out.println("Endpoints Requested :" + addBooksRequestTypes.getRequest());
        if (httpType.equalsIgnoreCase("post")) {
            System.out.println("Add Book request called");
            response = given().spec(reqSpecs.requestSpecification(url_requested.toLowerCase(Locale.ROOT))).
                    header("Content-Type", "application/json").
                    body(body).when().post(endPoints).then().extract().response();
        } else if (httpType.equalsIgnoreCase("get") && for_get.equalsIgnoreCase("author_name")) {
            response = given().spec(reqSpecs.requestSpecification(url_requested.toLowerCase(Locale.ROOT))).queryParam("AuthorName", author_name).
                    log().all()
                    .when().get(endPoints).then().extract().response();
        } else if (httpType.equalsIgnoreCase("get") && for_get.equalsIgnoreCase("id")) {
            response = given().spec(reqSpecs.requestSpecification(url_requested.toLowerCase(Locale.ROOT))).
                    queryParam("ID", System.getProperty("expectedID")).
                    log().all()
                    .when().get(endPoints).then().extract().response();
        } else if (httpType.equalsIgnoreCase("delete") && for_get.equalsIgnoreCase("id")) {
            response = given().spec(reqSpecs.requestSpecification(url_requested.toLowerCase(Locale.ROOT))).header("Content-Type", "application/json")
                    .log().all().when().body("{\n" +
                            "    \"ID\":\"" + System.getProperty("expectedID") + "\"\n" +
                            "}").when().delete(endPoints).then().extract().response();
        }
        System.out.println(httpType + ": Executed successfully, proceeding for next step");
    }

    @Then("validate response body contains {string},{string} and {string} used while hitting request")
    public void validate_Response_Body_Contains_And_Used_While_Hitting_Request(String isbnKey, String aisleKey, String book_name) {
        try {
            System.out.println("Validating presence of " + aisleKey + "," + isbnKey + "," + book_name);

            // Read expected values from system properties
            String expectedIsbn = System.getProperty(isbnKey);
            String expectedAisle = System.getProperty(aisleKey);
            String expectedAuthor = System.getProperty("author_name"); // make sure this is set before

            if (expectedIsbn == null || expectedAisle == null) {
                throw new IllegalStateException("Required system properties are not set: isbn=" + expectedIsbn + ", aisle=" + expectedAisle);
            }

            // Validate ISBN
            List<Object> isbn_lst = response.jsonPath().getList(isbnKey);
            boolean isbnPresent = isbn_lst.stream().anyMatch(s -> s.toString().equalsIgnoreCase(expectedIsbn));
            Assert.assertTrue(isbnPresent, "Expected ISBN not found: " + expectedIsbn);
            System.out.println(isbnKey + " Validated");

            // Validate AISLE
            List<Object> aisle_list = response.jsonPath().getList(aisleKey);
            boolean aislePresent = aisle_list.stream().anyMatch(s -> s.toString().equalsIgnoreCase(expectedAisle));
            Assert.assertTrue(aislePresent, "Expected AISLE not found: " + expectedAisle);
            System.out.println(aisleKey + " Validated");

            // Validate Book or Author
            if (book_name.equalsIgnoreCase("author")) {
                List<Object> authors = response.jsonPath().getList("author");
                System.out.println(authors + "*****************************");
                System.out.println("Expected Authors" + expectedAuthor + "*********************");
                boolean authorNamePresent = authors.stream().anyMatch(s -> s.toString().equalsIgnoreCase(expectedAuthor));
                Assert.assertTrue(authorNamePresent, "Expected Author not Found: " + expectedAuthor);
            } else {
                List<Object> book_names = response.jsonPath().getList("book_name");
                boolean bookNamesPresent = book_names.stream().anyMatch(s -> s.toString().equalsIgnoreCase(book_name));
                Assert.assertTrue(bookNamesPresent, "Expected BOOK not found: " + book_name);
            }

            System.out.println("Validated presence of " + aisleKey + "," + isbnKey + "," + book_name);

        } catch (Exception e) {
            System.out.println("Error Occurred in validating response body: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
