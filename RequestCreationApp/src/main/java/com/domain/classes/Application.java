package com.domain.classes;

import com.domain.classes.differentApproach.UserID;
import com.helper.CategoryID;
import com.helper.Status;

import java.util.Date;

public class Application {
    private final UserID userID;
    private final ApplicationID applicationID;
    private final CategoryID categoryID;
    private final Date startDate;
    private final Date endDate;
    private final Date initialisingDate;
    private final String requirements;
    private final Status status;

    public Application(UserID userID, CategoryID categoryID, Date startDate, Date endDate, String requirements, Status status, ApplicationID applicationID, Date initialisingDate) {
        this.userID = userID;
        this.applicationID = applicationID;
        this.categoryID = categoryID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requirements = requirements;
        this.status = status;
        this.initialisingDate = initialisingDate;
    }


    public UserID getUserID() {
        return userID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Status getStatus() {
        return status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public CategoryID getCategoryID() {
        return categoryID;
    }

    public String getRequirements() {
        return requirements;
    }

    public ApplicationID getApplicationID() {
        return applicationID;
    }

    public Date getInitialisingDate() {
        return initialisingDate;
    }
}
