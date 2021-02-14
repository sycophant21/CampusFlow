package com.managers;

import com.dao.UserDAO;
import com.domain.classes.differentApproach.User;
import com.domain.classes.differentApproach.UserID;
import com.domain.classes.differentApproach.UserPersonalDetails;
import com.helper.UserType;

import java.util.HashMap;

public class UserManager {
    private final UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserFromUserID(UserID id) {
        return userDAO.getUserBasedOnUserId(id);
    }

    public boolean createUser(User tempUser) {
        String userName = tempUser.getUserPersonalDetails().getUserName();
        String emailId = tempUser.getUserPersonalDetails().getEmailId();
        String password = tempUser.getUserPersonalDetails().getPassword();
        String userId = emailId.substring(0,emailId.indexOf("@")).toUpperCase();
        UserID userID = new UserID(userId);
        UserPersonalDetails userPersonalDetails = new UserPersonalDetails(userName,userID,emailId,password);
        User user = null;
        if (tempUser.getUserType() == UserType.APPLICANT) {
            user = new User(userPersonalDetails, tempUser.getUserType());
        }
        else if (tempUser.getUserType() == UserType.AUTHENTICATOR) {
            user = new User(userPersonalDetails, new HashMap<>(),tempUser.getUserType());
        }
        if (userDAO.getUserBasedOnUserId(user.getUserPersonalDetails().getUserID()) == null) {
            return userDAO.createUser(user);
        } else {
            return false;
        }
    }


}
