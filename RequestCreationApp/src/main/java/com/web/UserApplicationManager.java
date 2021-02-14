package com.web;

import com.domain.classes.differentApproach.UserID;
import com.helper.Serializer;
import com.managers.ApplicationManager;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class UserApplicationManager extends HttpServlet {
    private final Serializer serializer;
    private final ApplicationManager applicationManager;

    public UserApplicationManager(Serializer serializer, ApplicationManager applicationManager) {
        this.serializer = serializer;
        this.applicationManager = applicationManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String details = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        UserID userID = (UserID) serializer.deserialize(details, UserID.class);
        resp.getWriter().println(serializer.serialize(applicationManager.getApplicationsForUserID(userID)));
    }
}
