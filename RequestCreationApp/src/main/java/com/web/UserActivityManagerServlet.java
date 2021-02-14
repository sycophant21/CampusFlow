package com.web;

import com.domain.classes.differentApproach.TempUser;
import com.domain.classes.differentApproach.TempUserID;
import com.domain.classes.differentApproach.User;
import com.domain.classes.differentApproach.UserID;
import com.helper.ReturnStatus;
import com.helper.Serializer;
import com.managers.UserManager;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class UserActivityManagerServlet extends HttpServlet {
    private final Serializer serializer;
    private final UserManager userManager;

    public UserActivityManagerServlet(Serializer serializer, UserManager userManager) {
        this.serializer = serializer;
        this.userManager = userManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String details = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        Map<String, String> stringMap = (Map<String, String>) serializer.deserialize(details, HashMap.class);
        String userName = stringMap.get("userId");
        String password = stringMap.get("password");
        UserID userID = new UserID(userName);
        User user = userManager.getUserFromUserID(userID);
        if (user.getUserPersonalDetails().getPassword().equalsIgnoreCase(password)) {
            TempUser tempUser = new TempUser(new TempUserID(user.getUserPersonalDetails().getUserID().getUserID()), user.getUserType());
            resp.getWriter().println(serializer.serialize(tempUser));
        } else {
            resp.getWriter().println(serializer.serialize(null));
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String details = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        User applicant = (User) serializer.deserialize(details, User.class);
        if (userManager.createUser(applicant)) {
            resp.getWriter().println(serializer.serialize(new ReturnStatus("User creation Successful")));

        } else {
            resp.getWriter().println(serializer.serialize(new ReturnStatus("User creation Unsuccessful")));
        }
    }
}
