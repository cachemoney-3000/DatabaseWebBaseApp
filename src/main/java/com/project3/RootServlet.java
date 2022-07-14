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
        try {
            statement = initialize.initializeServlet(config, context, filepath);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String buttonClicked = request.getParameter("logoutButton");
        String path = "/root.jsp";

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
        try {
            utility.doPostHelper(request, "root");
        } catch (SQLException e) {
            // Show the error to the user
            String execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">" + e.getMessage() + "</p></div>";
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("execute", execute);
        }

        // Insert the update to the .jsp file
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/root.jsp");
        dispatcher.forward(request, response);
    }
}