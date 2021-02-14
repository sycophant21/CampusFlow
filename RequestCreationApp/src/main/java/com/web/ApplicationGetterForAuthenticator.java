package com.web;

import com.domain.classes.Application;
import com.domain.classes.differentApproach.User;
import com.domain.classes.differentApproach.UserID;
import com.helper.Serializer;
import com.managers.ApplicationManager;
import com.managers.UserManager;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationGetterForAuthenticator extends HttpServlet {
    private final Serializer serializer;
    private final ApplicationManager applicationManager;
    private final UserManager userManager;

    public ApplicationGetterForAuthenticator(Serializer serializer, ApplicationManager applicationManager, UserManager userManager) {
        this.serializer = serializer;
        this.applicationManager = applicationManager;
        this.userManager = userManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String details = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        UserID authenticatorID = (UserID) serializer.deserialize(details, UserID.class);
        User authenticator = userManager.getUserFromUserID(authenticatorID);
        Map<String, Integer> categoryIDMap = authenticator.getCategoryAuthenticationMap();
        List<Application> applications = new ArrayList<>();
        for (String categoryID : categoryIDMap.keySet()) {
            applications.addAll(applicationManager.getApplicationsForAuthenticator(categoryID, categoryIDMap.get(categoryID)));
        }
        resp.getWriter().println(serializer.serialize(applications));
    }
}
