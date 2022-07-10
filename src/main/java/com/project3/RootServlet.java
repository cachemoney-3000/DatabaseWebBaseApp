/* Name: Joshua Samontanez
Course: CNT 4714 – Summer 2022 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 4, 2022
*/

package com.project3;

import java.io.*;
import java.sql.*;

import com.mysql.cj.util.Util;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class RootServlet extends HttpServlet {
    private transient Statement statement;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Get the path of the properties file
        String filepath = config.getInitParameter("properties");
        ServletContext context = getServletContext();
        // Connect to the database
        Initialize initialize = new Initialize();
        statement = initialize.initializeServlet(config, context, filepath);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utility utility = new Utility(statement);
        utility.doPostHelper(request, "root");

        // Insert the update to the .jsp file
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
        dispatcher.forward(request, response);
    }
}