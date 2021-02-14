package com.example.applicationcreator.web;

public class CurrentUser {
    private TempUserID userID;
    private String password;
    private UserType userType;
    private static CurrentUser currentUser = null;

    public static CurrentUser getInstance() {
        if(currentUser == null) {
            currentUser = new CurrentUser();
        }
        return currentUser;
    }

    public TempUserID getUserID() {
        return userID;
    }

    public void setUserID(TempUserID userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
