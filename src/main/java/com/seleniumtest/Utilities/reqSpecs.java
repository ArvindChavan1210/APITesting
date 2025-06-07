package com.seleniumtest.Utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class reqSpecs {

    private static RequestSpecification reqs;

    public static RequestSpecification requestSpecification() throws FileNotFoundException {
        String baseUri = propertiesReader.readData("googleAPI", "url");
        System.out.println("Base URI: " + baseUri); // ✅ Helpful for debugging
        RequestSpecBuilder req = new RequestSpecBuilder();
        PrintStream log = new PrintStream(new FileOutputStream("D:\\JAVA\\VS-CodePRactice\\selenium-testng-demo\\logging.txtg"));
        reqs = req.setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log))
//                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .build();
        return reqs;
    }
}



