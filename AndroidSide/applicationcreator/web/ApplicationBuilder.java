package com.example.applicationcreator.web;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class ApplicationBuilder implements Serializable {
    private Date endDate;
    private Date startDate;
    private TempUserID userID;
    private CategoryID categoryID;
    private String requirements;

    public ApplicationBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }
    public ApplicationBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }
    public ApplicationBuilder withUserID(TempUserID userID) {
        this.userID = userID;
        return this;
    }
    public ApplicationBuilder withCategoryID(CategoryID categoryID) {
        this.categoryID = categoryID;
        return this;
    }
    public ApplicationBuilder withRequirements(String requirements) {
        this.requirements = requirements;
        return this;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Application build() {
        return new Application(userID,null,categoryID,startDate,endDate,Date.from(Instant.now()),requirements,null);
    }

    public TempUserID getUserID() {
        return userID;
    }

    public CategoryID getCategoryID() {
        return categoryID;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getRequirements() {
        return requirements;
    }
}
