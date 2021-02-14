package com.domain.classes.differentApproach;

public class UserPersonalDetails {
    private final String userName;
    private final UserID userID;
    private final String emailId;
    private final String password;

    public UserPersonalDetails(String userName, UserID userID, String emailId, String password) {
        this.userName = userName;
        this.userID = userID;
        this.emailId = emailId;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public UserID getUserID() {
        return userID;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }
}
