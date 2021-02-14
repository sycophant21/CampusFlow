package com.domain.classes.differentApproach;

import com.helper.UserType;

public class TempUser {
    private final TempUserID tempUserID;
    private final UserType userType;

    public TempUser(TempUserID tempUserID, UserType userType) {
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
