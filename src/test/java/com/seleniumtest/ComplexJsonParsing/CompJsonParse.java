package com.seleniumtest.ComplexJsonParsing;

import org.testng.annotations.Test;

import com.seleniumtest.Files.ComplexJsonPayload;

import io.restassured.path.json.JsonPath;

public class CompJsonParse {

    // 1. Print Number of Courses returned by API
    // 2. Print purchase amount
    // 3. Print tilte of first course
    // 4. Print All course title and their respective price
    // 5. Print Number of copies sold by RPA Course
    // 6. Verify sum of all course price matches with purchase amount

    JsonPath path = new JsonPath(ComplexJsonPayload.CoursePrice());

    @Test
    public void numberofCourse() {
        System.out.println("Number of Courses returned :" + path.getList("courses.title").size());

    }

    @Test
    public void getPurchaseAmount() {
        System.out.println("Purchase Amount :" + path.getString("dashboard.purchaseAmount"));
    }

    @Test
    public void getTitleofFirstCourse() {
        System.out.println("Title of First Course :" + path.getList("courses.title").get(0));
    }

    @Test
    public void courseTitleandPrice() {
        for (int i = 0; i < path.getList("courses").size(); i++) {
            System.out.println("Course Name :" + path.getList("courses.title").get(i) + " & Price :"
                    + path.getList("courses.price").get(i));
        }
    }

    @Test
    public void numberofCopiesSold() {
        for (int i = 0; i < path.getList("courses").size(); i++) {
            if (path.getList("courses.title").get(i).toString().trim().equalsIgnoreCase("RPA")) {
                System.out.println("Number of Copies Sold for RPA Course :"+path.getList("courses.copies").get(i));
            }
        }
    }

    @Test
    public void sumofAllCourses(){
        int purchaseAmount=path.get("dashboard.purchaseAmount");
        int totalAmount = 0;
        int amount=0;
        for(int i=0; i<path.getList("courses").size(); i++){
            amount=path.getInt("courses["+i+"].copies")*path.getInt("courses["+i+"].price");
            totalAmount+=amount;
        }
        System.out.println("Purchase Amount :"+purchaseAmount);
        System.out.println("Total Amount :"+totalAmount);
    }

}
