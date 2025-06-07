package com.BDD.BodyData;

import com.BDD.POJO.Location;
import com.BDD.POJO.addPlacePOJO;

import java.util.Arrays;
import java.util.List;

public class addPlaceBody {

    public static addPlacePOJO addPlaceData(){
        addPlacePOJO addPlace=new addPlacePOJO();
        addPlace.setAccuracy(70);
        addPlace.setAddress("9, side layout, cohen 09");
        addPlace.setName("Frontline House");
        addPlace.setPhone_number("+91 456789134");
        addPlace.setWebsite("https://maps.google.com");
        List<String> types = Arrays.asList("Type1", "Type2");
        addPlace.setTypes(types);
        addPlace.setLanguage("English-IN");
        Location location = new Location();
        location.setLat("31.4583");
        location.setLng("33.2546"); // ✅ FIXED: changed from setLan to setLng
        addPlace.setLocation(location);
        return addPlace;
    }

    public static String updatePlaceBody(String place_id, String address){
        return  "{\n" +
                "\"place_id\":\""+place_id+"\",\n" +
                "\"address\":\""+address+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }

    public static String deletePlaceBody(String place_id){
        return "{\n" +
                "    \"place_id\":\""+place_id+"\"\n" +
                "}\n";
    }
}
