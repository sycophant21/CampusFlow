package com.dao;

import com.helper.Category;
import com.helper.CategoryID;
import com.helper.Operator;
import com.helper.Serializer;
import com.helper.filters.SimpleFilter;
import com.mongo.MongoOperations;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryDAO {
    private final MongoOperations mongoOperations;
    private final Serializer serializer;
    private final String categoriesCollectionName = "";

    public CategoryDAO(MongoOperations mongoOperations, Serializer serializer) {
        this.mongoOperations = mongoOperations;
        this.serializer = serializer;
    }

    public List<Category> getCategories() {
        Iterator<Document> documentIterator = mongoOperations.getAll(categoriesCollectionName);
        List<Category> categoryList = new ArrayList<>();
        while (documentIterator.hasNext()) {
            categoryList.add((Category) serializer.deserialize(serializer.serialize(documentIterator.next()),Category.class));
        }
        return categoryList;
    }

    public boolean addCategory(Category category) {

        return mongoOperations.create(serializer.serialize(category),categoriesCollectionName);
    }

    public Category getCategory(CategoryID categoryID) {
        String categoryIdFieldPath = "";
        SimpleFilter simpleFilter = new SimpleFilter(Operator.EQ,categoryIdFieldPath,categoryID.getCategoryID());
        return (Category) serializer.deserialize(serializer.serialize(mongoOperations.get(categoriesCollectionName,simpleFilter).next()),Category.class);
    }
}
