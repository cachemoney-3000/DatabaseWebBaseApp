/* Name: Joshua Samontanez
Course: CNT 4714 – Summer 2022 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 4, 2022
*/

package com.project3;

import java.io.*;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

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
        String buttonClicked = request.getParameter("logoutButton");
        String path = "/root.jsp";
        // Change the path
        System.out.println(buttonClicked);
        if (buttonClicked.equals("Logout")){
            path = "/index.jsp";
        }

        // Invalidate the session when logging out
        HttpSession session = request.getSession();
        session.invalidate();

        // Forward the dispatcher based on the path String
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utility utility = new Utility(statement);
        utility.doPostHelper(request, "root");


        // Insert the update to the .jsp file
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
        dispatcher.forward(request, response);
    }
}