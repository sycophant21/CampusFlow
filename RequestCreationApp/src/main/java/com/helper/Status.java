package com.helper;

import com.domain.classes.differentApproach.UserID;

import java.sql.Time;
import java.util.List;

public class Status {
    private final int currentStatus;
    private Time lastModifiedTime;
    private final int finalStatus;
    private final StatusType statusType;
    private final List<UserID> authenticators;

    public Status(int currentStatus, Time lastModifiedTime, int finalStatus, StatusType statusType, List<UserID> authenticators) {
        this.currentStatus = currentStatus;
        this.lastModifiedTime = lastModifiedTime;
        this.finalStatus = finalStatus;
        this.statusType = statusType;
        this.authenticators = authenticators;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public int getFinalStatus() {
        return finalStatus;
    }

    public boolean addAuthenticators(UserID authenticatorID) {
        return authenticators.add(authenticatorID);
    }

    public List<UserID> getAuthenticators() {
        return authenticators;
    }

    public Time getLastModifiedTime() {
        return lastModifiedTime;
    }
    public void setLastModifiedTime(Time lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public StatusType getStatusType() {
        return statusType;
    }
}
