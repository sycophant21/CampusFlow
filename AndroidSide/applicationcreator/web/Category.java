package com.example.applicationcreator.web;

public class Category {
    private final String categoryName;
    private final CategoryID categoryID;

    public Category(String categoryName, CategoryID categoryID) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public CategoryID getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        //System.out.println(categoryName);
        return categoryName;
    }
}
