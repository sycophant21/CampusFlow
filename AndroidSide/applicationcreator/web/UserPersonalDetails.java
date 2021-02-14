package com.example.applicationcreator.web;

public class UserPersonalDetails {
    private final String userName;
    private final String emailId;
    private final String password;

    public UserPersonalDetails(String userName, String emailId, String password) {
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }
}
