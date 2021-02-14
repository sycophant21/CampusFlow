package com.example.applicationcreator.web;

import java.sql.Time;
import java.util.List;

public class Status {
    private final int currentStatus;
    private Time lastModifiedTime;
    private final int finalStatus;
    private final StatusType statusType;
    private final List<TempUserID> authenticators;

    public Status(int currentStatus, Time lastModifiedTime, int finalStatus, StatusType statusType, List<TempUserID> authenticators) {
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

    public List<TempUserID> getAuthenticators() {
        return authenticators;
    }

    public Time getLastModifiedTime() {
        return lastModifiedTime;
    }

    public StatusType getStatusType() {
        return statusType;
    }
}
