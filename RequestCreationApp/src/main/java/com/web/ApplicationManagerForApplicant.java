package com.web;

import com.domain.classes.Application;
import com.domain.classes.ApplicationID;
import com.google.gson.Gson;
import com.helper.ReturnStatus;
import com.helper.Serializer;
import com.managers.ApplicationManager;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class ApplicationManagerForApplicant extends HttpServlet {
    private final Serializer serializer;
    private final ApplicationManager applicationManager;
    private final Gson gson;

    public ApplicationManagerForApplicant(Serializer serializer, ApplicationManager applicationManager, Gson gson) {

        this.serializer = serializer;
        this.applicationManager = applicationManager;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String details = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        ApplicationID applicationID = gson.fromJson(details, ApplicationID.class);
        resp.getWriter().println(serializer.serialize(applicationManager.getApplicationForApplicationID(applicationID)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String applicationAsString = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        Application pseudoApplication = (Application) serializer.deserialize(applicationAsString, Application.class);
        if (applicationManager.createNewApplication(pseudoApplication)) {
            resp.getWriter().println(serializer.serialize(new ReturnStatus("Application Created Successfully")));
        } else {
            resp.getWriter().println(serializer.serialize("Application Couldn't Be Created, Try Again Later"));
        }
    }
}
