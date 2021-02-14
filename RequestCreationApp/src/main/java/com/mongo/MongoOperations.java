package com.mongo;

import com.helper.filters.Filter;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Iterator;

public class MongoOperations {
    private final MongoDatabase mongoDatabase;
    private final FilterToBSONConverter filterToBSONConverter;

    public MongoOperations(MongoDatabase mongoDatabase, FilterToBSONConverter filterToBSONConverter) {
        this.mongoDatabase = mongoDatabase;
        this.filterToBSONConverter = filterToBSONConverter;
    }

    public boolean create(String json, String collectionName) {

        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        InsertOneResult insertOneResult = mongoCollection.insertOne(Document.parse(json));
        return insertOneResult.wasAcknowledged();
    }

    public boolean update(String oldRecord, String newRecord, String collectionName) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        UpdateResult updateResult = mongoCollection.replaceOne(Document.parse(oldRecord), Document.parse(newRecord));
        return updateResult.wasAcknowledged();
    }

    public Iterator<Document> get(String collectionName, Filter filter) {
        Bson bson = filterToBSONConverter.filterToBsonConverter(filter);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        return mongoCollection.find(bson).iterator();
    }

    public Iterator<Document> getAll(String collectionName) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        return mongoCollection.find().iterator();
    }
}