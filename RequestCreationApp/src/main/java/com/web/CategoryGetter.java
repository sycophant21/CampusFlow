package com.web;

import com.helper.Serializer;
import com.managers.CategoryManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CategoryGetter extends HttpServlet {
    private final CategoryManager categoryManager;
    private final Serializer serializer;

    public CategoryGetter(CategoryManager categoryManager, Serializer serializer) {
        this.categoryManager = categoryManager;
        this.serializer = serializer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(serializer.serialize(categoryManager.getCategories()));
    }
}
