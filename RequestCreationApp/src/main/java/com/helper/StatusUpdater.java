package com.helper;

import com.domain.classes.ApplicationID;
import com.domain.classes.differentApproach.UserID;

public class StatusUpdater {
    private final UserID userID;
    private final ApplicationID applicationID;
    private final int action;

    public StatusUpdater(UserID userID, ApplicationID applicationID, int action) {
        this.userID = userID;
        this.applicationID = applicationID;
        this.action = action;
    }

    public UserID getUserID() {
        return userID;
    }

    public ApplicationID getApplicationID() {
        return applicationID;
    }

    public int getAction() {
        return action;
    }
}
