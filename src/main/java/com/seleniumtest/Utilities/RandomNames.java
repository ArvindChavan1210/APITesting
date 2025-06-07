package com.seleniumtest.Utilities;

import java.util.Random;

public class RandomNames {

    private static String[] firstNames = { "John", "Jane", "Michael", "Emily", "David", "Sophia", "Chris", "Olivia",
            "James", "Emma" };
    private static String[] lastNames = { "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
            "Martinez",
            "Anderson" };

    public static String randomFullName() {
        Random random = new Random();
        String randName = firstNames[random.nextInt(firstNames.length)] + " "
                + lastNames[random.nextInt(lastNames.length)];
        return randName;
    }


    public static String randomChars(){
        String data="abcdefghijklmnopqrstuvwxyz";
        Random random=new Random();
        String threeChars="";
        for(int i=0; i<3; i++){
            threeChars+=data.charAt(random.nextInt(data.length()));
        }
        return threeChars;
    }


    public static void main(String[] args) {
        System.out.println(randomFullName());
        System.out.println(randomChars());
    }

}
