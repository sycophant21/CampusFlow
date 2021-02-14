package com.managers;

import com.dao.ApplicationDAO;
import com.dao.UserDAO;
import com.domain.classes.Application;
import com.domain.classes.ApplicationID;
import com.domain.classes.differentApproach.User;
import com.domain.classes.differentApproach.UserID;
import com.helper.*;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicationManager {
    private final ApplicationDAO applicationDAO;
    private final CategoryManager categoryManager;
    private final UserDAO userDAO;

    public ApplicationManager(ApplicationDAO applicationDAO, CategoryManager categoryManager, UserDAO userDAO) {
        this.applicationDAO = applicationDAO;
        this.categoryManager = categoryManager;
        this.userDAO = userDAO;
    }

    public List<Application> getApplicationsForUserID(UserID userID) {
        User user = userDAO.getUserBasedOnUserId(userID);
        List<Application> applicationList = new ArrayList<>();
        if (user.getUserType() == UserType.APPLICANT) {
             applicationList.addAll(applicationDAO.getApplicationsForApplicantID(userID));
        }
        else if (user.getUserType() == UserType.AUTHENTICATOR) {
            for (String categoryID : user.getCategoryAuthenticationMap().keySet()) {
                applicationList.addAll(getApplicationsForAuthenticator(categoryID, user.getCategoryAuthenticationMap().get(categoryID)));
            }
        }
        return applicationList;
    }
    public Application getApplicationForApplicationID(ApplicationID applicationID) {
        return applicationDAO.getApplicationForApplicationID(applicationID);
    }

    public boolean createNewApplication(Application application) {
        UserID userID = application.getUserID();
        CategoryID categoryID = application.getCategoryID();
        Date startDate = application.getStartDate();
        Date endDate = application.getEndDate();
        String requirements = application.getRequirements();
        Category category = categoryManager.getCategory(categoryID);
        Status status = new Status(0, Time.valueOf(LocalTime.now()),category.getNumberOfAuthorizersRequired(), StatusType.PENDING, new ArrayList<>());
        ApplicationID applicationID = new ApplicationID(userID.getUserID()+""+categoryID+""+startDate.toString()+""+endDate.toString());
        Application applicationToBeCreated = new Application(userID,categoryID,startDate,endDate,requirements,status,applicationID, Date.from(Instant.now()));
        return applicationDAO.createApplication(applicationToBeCreated);
    }

    public boolean updateStatusForApplicationID(ApplicationID applicationID, int newCurrentStatus, User newAuthenticator, StatusType newStatusType) {
        Application oldApplication = applicationDAO.getApplicationForApplicationID(applicationID);
        List<UserID> newAuthenticatorList = populateList(oldApplication.getStatus().getAuthenticators());
        Application newApplication = new Application(oldApplication.getUserID(),
                                                    oldApplication.getCategoryID(),
                                                    oldApplication.getStartDate(),
                                                    oldApplication.getEndDate(),
                                                    oldApplication.getRequirements(),
                                                    new Status(newCurrentStatus,
                                                            Time.valueOf(LocalTime.now()),
                                                            oldApplication.getStatus().getFinalStatus(),
                                                            newStatusType, newAuthenticatorList),
                                                    oldApplication.getApplicationID(), oldApplication.getInitialisingDate());
        if (newApplication.getStatus().addAuthenticators(newAuthenticator.getUserPersonalDetails().getUserID())) {
            return applicationDAO.updateApplication(oldApplication, newApplication);
        }
        else {
            return false;
        }
    }

    public List<Application> getApplicationsForAuthenticator(String  authenticatorCategory, int authenticationLevelForThatCategory) {
        return applicationDAO.getApplicationBasedOnCategoryAndStatus(authenticatorCategory,authenticationLevelForThatCategory);
    }


    private List<UserID> populateList(List<UserID> currentAuthenticatorList) {
        return new ArrayList<>(currentAuthenticatorList);
    }
}
