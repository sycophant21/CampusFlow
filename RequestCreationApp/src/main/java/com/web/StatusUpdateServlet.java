package com.web;

import com.domain.classes.Application;
import com.domain.classes.differentApproach.User;
import com.google.gson.Gson;
import com.helper.ReturnStatus;
import com.helper.StatusType;
import com.helper.StatusUpdater;
import com.managers.ApplicationManager;
import com.managers.UserManager;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class StatusUpdateServlet extends HttpServlet {
    private final Gson gson;
    private final ApplicationManager applicationManager;
    private final UserManager userManager;

    public StatusUpdateServlet(Gson gson, ApplicationManager applicationManager, UserManager userManager) {
        this.gson = gson;
        this.applicationManager = applicationManager;
        this.userManager = userManager;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String details = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        StatusUpdater statusUpdater = gson.fromJson(details, StatusUpdater.class);
        User user = userManager.getUserFromUserID(statusUpdater.getUserID());
        Application application = applicationManager.getApplicationForApplicationID(statusUpdater.getApplicationID());
        int level = user.getCategoryAuthenticationMap().get(application.getCategoryID().getCategoryID());
        boolean returnStatus;
        if (statusUpdater.getAction() == 0) {
            returnStatus = applicationManager.updateStatusForApplicationID(statusUpdater.getApplicationID(), level, user, StatusType.REJECTED);
        } else {
            if (application.getStatus().getFinalStatus() == application.getStatus().getCurrentStatus() + 1) {
                returnStatus = applicationManager.updateStatusForApplicationID(application.getApplicationID(), level + 1, user, StatusType.ACCEPTED);
            } else {
                returnStatus = applicationManager.updateStatusForApplicationID(application.getApplicationID(), level + 1, user, StatusType.PENDING);
            }
        }
        if (returnStatus) {
            resp.getWriter().println(gson.toJson(new ReturnStatus("OK")));
        } else {
            resp.getWriter().println(gson.toJson(new ReturnStatus("Error")));
        }
    }
}
