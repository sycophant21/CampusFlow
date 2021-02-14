package com.example.applicationcreator.web;

public class StatusUpdater {
    private final TempUserID userID;
    private final ApplicationID applicationID;
    private final int action;

    public StatusUpdater(TempUserID userID, ApplicationID applicationID, int action) {
        this.userID = userID;
        this.applicationID = applicationID;
        this.action = action;
    }

    public TempUserID getUserID() {
        return userID;
    }

    public ApplicationID getApplicationID() {
        return applicationID;
    }

    public int getAction() {
        return action;
    }
}

