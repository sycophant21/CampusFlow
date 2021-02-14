package com.example.applicationcreator.web;

import java.util.Date;

public class Application {
    private final TempUserID userID;
    private final ApplicationID applicationID;
    private final CategoryID categoryID;
    private final Date startDate;
    private final Date endDate;
    private final Date initialisingDate;
    private final String requirements;
    private final Status status;

    public Application(TempUserID userID, ApplicationID applicationID, CategoryID categoryID, Date startDate, Date endDate, Date initialisingDate, String requirements, Status status) {
        this.userID = userID;
        this.applicationID = applicationID;
        this.categoryID = categoryID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.initialisingDate = initialisingDate;
        this.requirements = requirements;
        this.status = status;
    }

    public ApplicationID getApplicationID() {
        return applicationID;
    }

    public CategoryID getCategoryID() {
        return categoryID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getInitialisingDate() {
        return initialisingDate;
    }

    public String getRequirements() {
        return requirements;
    }

    public Status getStatus() {
        return status;
    }

    public TempUserID getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "Application{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", initialisingDate=" + initialisingDate +
                "}\n";
    }
}
