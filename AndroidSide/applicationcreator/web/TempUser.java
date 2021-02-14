package com.example.applicationcreator.web;

import java.io.Serializable;

public class TempUser implements Serializable {

    private final TempUserID tempUserID;
    private final UserType userType;

    public TempUser(TempUserID tempUserID, UserType userType, String password) {
        this.tempUserID = tempUserID;
        this.userType = userType;
    }

    public TempUserID getTempUserID() {
        return tempUserID;
    }

    public UserType getUserType() {
        return userType;
    }
}
