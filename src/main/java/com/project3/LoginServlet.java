package com.project3;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Find out which button was clicked by the user
        String buttonClicked = request.getParameter("login");
        System.out.println(buttonClicked);
        // Get the inputs from the input fields
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String properties = request.getParameter("properties");

        System.out.println(username + " " + password + " " + properties);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/data-entry.jsp");
        dispatcher.forward(request, response);
    }
}
