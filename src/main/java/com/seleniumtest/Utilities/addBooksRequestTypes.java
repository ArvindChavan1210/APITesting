package com.seleniumtest.Utilities;

public enum addBooksRequestTypes {

    post("/Library/Addbook.php"),
    get("/Library/GetBook.php"),
    delete("/Library/DeleteBook.php");

    final String requestType;

    addBooksRequestTypes(String requestType) {
        this.requestType = requestType;
    }

    public String getRequest() {
        return requestType;
    }
}
