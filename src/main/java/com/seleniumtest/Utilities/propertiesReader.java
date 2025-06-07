package com.seleniumtest.Utilities;

import java.io.InputStream;
import java.util.Properties;

public class propertiesReader {

    public static String readData(String fileName, String value) {
        try {
            InputStream input = propertiesReader.class.getClassLoader().getResourceAsStream("googleAPI.properties");
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
       System.out.println(readData("googleAPI", "url"));
    }

}
