package com.managers;

import com.dao.CategoryDAO;
import com.helper.Category;
import com.helper.CategoryID;

import java.util.List;

public class CategoryManager {
    private final CategoryDAO categoryDAO;

    public CategoryManager(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
    public List<Category> getCategories() {
        List<Category> categoryList = categoryDAO.getCategories();
        if (categoryList.isEmpty()) {
            return null;
        }
        else {
            return categoryList;
        }
    }
    public boolean insertCategory(Category category) {
        return categoryDAO.addCategory(category);
    }
    public Category getCategory(CategoryID categoryID) {
        return categoryDAO.getCategory(categoryID);
    }
}
