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

public class ClientServlet extends HttpServlet {
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String buttonClicked = request.getParameter("logoutButton");
        String path = "/client.jsp";

        // Change the path and close the database when logout button is clicked
        System.out.println(buttonClicked);
        if (buttonClicked.equals("Logout")){
            Utility utility = new Utility(statement);
            path  = utility.logout(request);
        }

        // Forward the dispatcher based on the path String
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utility utility = new Utility(statement);
        utility.doPostHelper(request, "client");


        // Insert the update to the .jsp file
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/client.jsp");
        dispatcher.forward(request, response);
    }
}