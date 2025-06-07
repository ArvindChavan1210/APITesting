package com.seleniumtest.Utilities;

public enum addPlaceRequestTypes {

    add("/add/json"),
    get("/get/json"),
    update("/update/json"),
    delete("/delete/json");

    private final String resource;

    addPlaceRequestTypes(String resource){
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
