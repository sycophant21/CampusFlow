package com.example.applicationcreator.web;

import java.io.Serializable;

public class TempUserID implements Serializable {
    private final String userID;

    public TempUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
