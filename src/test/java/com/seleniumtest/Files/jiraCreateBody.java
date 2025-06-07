package com.seleniumtest.Files;

public class jiraCreateBody {

    public static String bodyData(String issueType, String summary, String description) {
        String body = "{\r\n" + //
                "    \"fields\": {\r\n" + //
                "       \"project\":\r\n" + //
                "       {\r\n" + //
                "          \"key\": \"SCRUM\"\r\n" + //
                "       },\r\n" + //
                "       \"summary\": \"" + summary + "\",\r\n" + //
                "       \"description\": \"" + description + "\",\r\n" + //
                "       \"issuetype\": {\r\n" + //
                "          \"name\": \""+issueType+"\"\r\n" + //
                "       }\r\n" + //
                "   }\r\n" + //
                "}\r\n" + //
                "";
        return body;
    }

}
