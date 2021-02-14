package com.dao;

import com.domain.classes.Application;
import com.domain.classes.ApplicationID;
import com.domain.classes.differentApproach.UserID;
import com.helper.Operator;
import com.helper.Serializer;
import com.helper.filters.AndFilter;
import com.helper.filters.Filter;
import com.helper.filters.SimpleFilter;
import com.mongo.MongoOperations;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApplicationDAO {

    private final Serializer serializer;
    private final MongoOperations mongoOperations;
    //Enter the collection name in which the application are to stored.
    private final String collectionName = "";

    public ApplicationDAO(Serializer serializer, MongoOperations mongoOperations) {

        this.serializer = serializer;
        this.mongoOperations = mongoOperations;
    }


    public boolean createApplication(Application application) {
        return mongoOperations.create(serializer.serialize(application), collectionName);
    }


    public boolean updateApplication(Application oldApplication, Application newApplication) {
        return mongoOperations.update(serializer.serialize(oldApplication), serializer.serialize(newApplication), "Applications");
    }


    public List<Application> getApplicationsForApplicantID(UserID userID) {
        //Field Path of the user id object
        String fieldPath = "";
        SimpleFilter simpleFilter = new SimpleFilter(Operator.EQ, fieldPath, userID.getUserID());
        return getApplications(simpleFilter);
    }


    public Application getApplicationForApplicationID(ApplicationID applicationID) {
        //Field Path of the application id object
        String fieldPath = "";
        SimpleFilter simpleFilter = new SimpleFilter(Operator.EQ, fieldPath, applicationID.getApplicationID());
        Iterator<Document> documentIterator = mongoOperations.get(collectionName, simpleFilter);
        if (documentIterator.hasNext()) {
            return (Application) serializer.deserialize(serializer.serialize(documentIterator.next()), Application.class);
        } else {
            return null;
        }
    }

    public List<Application> getApplicationBasedOnCategoryAndStatus(String categoryID, int level) {
        //Field Path of the category id object
        String categoryFieldPath = "";
        //Field Path of the status object
        String statusFieldPath = "";
        SimpleFilter simpleFilter = new SimpleFilter(Operator.EQ, categoryFieldPath, categoryID);
        SimpleFilter simpleFilter1 = new SimpleFilter(Operator.EQ, statusFieldPath, level);
        List<Filter> filters = new ArrayList<>();
        filters.add(simpleFilter);
        filters.add(simpleFilter1);
        AndFilter andFilter = new AndFilter(filters);
        return getApplications(andFilter);
    }

    private List<Application> getApplications(Filter filter) {
        Iterator<Document> documentIterator = mongoOperations.get(collectionName, filter);
        List<Application> applicationList = new ArrayList<>();
        while (documentIterator.hasNext()) {
            applicationList.add((Application) serializer.deserialize(serializer.serialize(documentIterator.next()), Application.class));
        }
        return applicationList;
    }
}
