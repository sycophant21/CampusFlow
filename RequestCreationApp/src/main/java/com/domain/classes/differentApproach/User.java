package com.domain.classes.differentApproach;

import com.helper.UserType;

import java.util.Map;

public class User {
    private final UserPersonalDetails userPersonalDetails;
    private final Map<String,Integer> categoryAuthenticationMap;
    private final UserType userType;

    public User(UserPersonalDetails userPersonalDetails, Map<String, Integer> categoryAuthenticationMap, UserType userType) {
        this.userPersonalDetails = userPersonalDetails;
        this.categoryAuthenticationMap = categoryAuthenticationMap;
        this.userType = userType;
    }
    public User(UserPersonalDetails userPersonalDetails, UserType userType) {
        this.userPersonalDetails = userPersonalDetails;
        this.userType = userType;
        this.categoryAuthenticationMap = null;
    }

    public UserPersonalDetails getUserPersonalDetails() {
        return userPersonalDetails;
    }

    public Map<String, Integer> getCategoryAuthenticationMap() {
        return categoryAuthenticationMap;
    }

    public UserType getUserType() {
        return userType;
    }
}
