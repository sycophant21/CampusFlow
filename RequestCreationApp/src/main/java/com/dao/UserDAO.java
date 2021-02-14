package com.dao;

import com.domain.classes.differentApproach.User;
import com.domain.classes.differentApproach.UserID;
import com.helper.Operator;
import com.helper.Serializer;
import com.helper.filters.SimpleFilter;
import com.mongo.MongoOperations;
import org.bson.Document;

import java.util.Iterator;

public class UserDAO {

    private final Serializer serializer;
    private final MongoOperations mongoOperations;
    private final String userCollectionName = "";

    public UserDAO(Serializer serializer, MongoOperations mongoOperations) {
        this.serializer = serializer;
        this.mongoOperations = mongoOperations;
    }
    public User getUserBasedOnUserId(UserID id) {
        String userIdFieldPath = "";
        SimpleFilter simpleFilter = new SimpleFilter(Operator.EQ,userIdFieldPath,id.getUserID().toUpperCase());
        Iterator<Document> documentIterator = mongoOperations.get(userCollectionName,simpleFilter);
        if (documentIterator.hasNext()) {
            return (User) serializer.deserialize(serializer.serialize(documentIterator.next()),User.class);
        }
        else {
            return null;
        }
    }
    public boolean createUser(User user) {
        return mongoOperations.create(serializer.serialize(user),userCollectionName);
    }
}
