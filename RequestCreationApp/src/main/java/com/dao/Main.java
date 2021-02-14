package com.dao;

import com.google.gson.Gson;
import com.helper.GsonCreator;
import com.helper.Serializer;
import com.managers.ApplicationManager;
import com.managers.CategoryManager;
import com.managers.UserManager;
import com.mongo.FilterToBSONConverter;
import com.mongo.MongoOperations;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.web.*;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);
        //Enter the mongo connection id.
        MongoClient mongoClient = MongoClients.create("");
        //Enter the mongo database to be used.
        MongoDatabase database = mongoClient.getDatabase("");
        GsonCreator gsonCreator = new GsonCreator(new Gson());
        Gson gson = gsonCreator.getGson();
        MongoOperations mongoOperations = new MongoOperations(database, new FilterToBSONConverter());
        UserDAO userDAO = new UserDAO(new Serializer(gson),mongoOperations);
        UserManager userManager = new UserManager(userDAO);
        ApplicationManager applicationManager = new ApplicationManager(new ApplicationDAO(new Serializer(gson),mongoOperations), new CategoryManager(new CategoryDAO(mongoOperations,new Serializer(gson))), new UserDAO(new Serializer(gson),mongoOperations));
        servletHandler.addServletWithMapping(new ServletHolder(new ApplicationManagerForApplicant(new Serializer(gson), applicationManager, gson)),"/manageApplications");
        servletHandler.addServletWithMapping(new ServletHolder(new CategoryGetter(new CategoryManager(new CategoryDAO(mongoOperations,new Serializer(gson))), new Serializer(gson))),"/getCategories");
        servletHandler.addServletWithMapping(new ServletHolder(new UserActivityManagerServlet(new Serializer(gson),userManager)),"/login");
        servletHandler.addServletWithMapping(new ServletHolder(new UserActivityManagerServlet(new Serializer(gson),userManager)),"/signUp");
        servletHandler.addServletWithMapping(new ServletHolder(new UserApplicationManager(new Serializer(gson), applicationManager)),"/getUserApplications");
        servletHandler.addServletWithMapping(new ServletHolder(new ApplicationGetterForAuthenticator(new Serializer(gson),applicationManager,userManager)),"/getAuthenticatorApplications");
        servletHandler.addServletWithMapping(new ServletHolder(new StatusUpdateServlet(gson, applicationManager, userManager)),"/updateStatus");
        server.start();
    }
}