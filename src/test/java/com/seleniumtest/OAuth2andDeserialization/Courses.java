package com.seleniumtest.OAuth2andDeserialization;

import java.util.List;

public class Courses {
    public List<webAutomation> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<webAutomation> webAutomations) {
        this.webAutomation = webAutomations;
    }

    public List<com.seleniumtest.OAuth2andDeserialization.api> getApi() {
        return api;
    }

    public void setApi(List<com.seleniumtest.OAuth2andDeserialization.api> api) {
        this.api = api;
    }

    public List<mobile> getMobile() {
        return mobile;
    }

    public void setMobile(List<mobile> mobiles) {
        this.mobile = mobiles;
    }

    List<webAutomation> webAutomation;
    List<api> api;
    List<mobile> mobile;

}
