package com.seleniumtest.Files;

public class AddBookBody {

    public static String bodyData(String bookName,String ISBN, String aisle, String name) {
        String data = "{\r\n" + //
                "    \"name\": \""+bookName+"\",\r\n" + //
                "    \"isbn\": \"" + ISBN + "\",\r\n" + //
                "    \"aisle\": \"" + aisle + "\",\r\n" + //
                "    \"author\": \"" + name + "\"\r\n" + //
                "}";
        return data;
    }

}
