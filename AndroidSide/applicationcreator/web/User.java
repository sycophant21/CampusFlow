package com.example.applicationcreator.web;

public class User {
    private final UserPersonalDetails userPersonalDetails;
    private final UserType userType;

    public User(UserPersonalDetails userPersonalDetails, UserType userType) {
        this.userPersonalDetails = userPersonalDetails;
        this.userType = userType;
    }

    public UserPersonalDetails getUserPersonalDetails() {
        return userPersonalDetails;
    }
}
