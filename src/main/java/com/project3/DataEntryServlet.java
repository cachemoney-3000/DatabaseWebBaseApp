/* Name: Joshua Samontanez
Course: CNT 4714 – Summer 2022 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 4, 2022
*/

package com.project3;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DataEntryServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Start the session
        HttpSession session = request.getSession();
        // Find out which button was clicked by the user
        String buttonClicked = request.getParameter("button_clicked");
        System.out.println(buttonClicked);
        // Get the inputs from the input fields
        String jnum = request.getParameter("jnum");
        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String quantity = request.getParameter("quantity");
        String execute;

        // If the user clicked the execute button, run the query
        if (buttonClicked.equals("Execute")) {
            // If all fields are filled, execute the query
            if (!Objects.equals(jnum, "") && !Objects.equals(snum, "") && !Objects.equals(pnum, "") && !Objects.equals(quantity, "")) {
                System.out.println(snum + " " + jnum + " " + pnum + " " + quantity);
                // Convert inputs to uppercase
                snum = snum.toUpperCase();
                jnum = jnum.toUpperCase();
                pnum = pnum.toUpperCase();
                // Parse quantity to int, quantity shall be a number
                int q = -999;
                // Check if the input for quantity is a number
                try {
                    q = Integer.parseInt(quantity);
                }
                // If it's not a number, display an error to the user
                catch (NumberFormatException e){
                    execute = "<div class = \"executionContainer\"><p class = \"executionText\">Error: The input for quantity must be a number</p></div>";
                    session.setAttribute("execute", execute);
                }

                // Create a query using this format
                String query = "insert into shipments values (\""+ snum + "\", \""+ pnum + "\", \""+  jnum + "\"," + " " + q + ");";

                // Run the query
                Utility utility = new Utility(statement);
                try {
                    execute = utility.updateQueryDataEntry(query, q);
                    // Show the user what how many records was updated
                    session.setAttribute("execute", execute);
                } catch (SQLException e) {
                    // Show any errors to the user
                    execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">" + e.getMessage() + "</p></div>";
                    e.printStackTrace();
                    session.setAttribute("execute", execute);
                }

                System.out.println(query);
            }
            // If there is one or more fields empty, display error to the user
            else {
                execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">Error: One of the input fields are empty</p></div>";
                session.setAttribute("execute", execute);
            }
        }
        // If the user clicked the Clear button, clear the input fields and display a message
        else if (buttonClicked.equals("Clear")) {
            execute = "<div class = \"executionContainer\"><p class = \"executionText\">Input fields are cleared...</p></div>";
            session.setAttribute("execute", execute);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/data-entry.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buttonClicked = request.getParameter("logoutButton");
        String path = "/data-entry.jsp";

        // Change the path and close the database when logout button is clicked
        System.out.println(buttonClicked);
        if (buttonClicked.equals("Logout")){
            Utility utility = new Utility(statement);
            path  = utility.logout(request);
        }

        // Invalidate the session when logging out
        HttpSession session = request.getSession();
        session.invalidate();

        // Forward the dispatcher based on the path String
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
