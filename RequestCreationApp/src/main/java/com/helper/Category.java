package com.helper;

public class Category {
    private final String categoryName;
    private final CategoryID categoryID;
    private final int NumberOfAuthorizersRequired;

    public Category(String categoryName, String categoryID, int NumberOfAuthorizersRequired) {
        this.categoryName = categoryName;
        this.categoryID = new CategoryID(categoryID);
        this.NumberOfAuthorizersRequired = NumberOfAuthorizersRequired;
    }

    public CategoryID getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getNumberOfAuthorizersRequired() {
        return NumberOfAuthorizersRequired;
    }
}
