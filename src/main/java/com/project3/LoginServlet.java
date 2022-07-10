/* Name: Joshua Samontanez
Course: CNT 4714 – Summer 2022 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 4, 2022
*/

package com.project3;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Find out which button was clicked by the user
        String buttonClicked = request.getParameter("login");
        System.out.println(buttonClicked);
        // Get the inputs from the input fields
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String properties = request.getParameter("properties");
        HttpSession session = request.getSession();
        String location = "index.jsp";

        System.out.println(username + " " + password + " " + properties);

        // If the button was clicked, verify the credentials that the user put
        if (buttonClicked.equals("Login"))
            location = validate(username, password, properties, location, session);


        response.sendRedirect(location);
    }

    private String validate(String username, String password, String properties, String location, HttpSession session){
        String path;
        String execute;
        // All the fields are filled
        if (!username.equals("") && !password.equals("") && properties != null) {
            // Check what is the property type the user chose
            if (properties.equals("root")){
                path = "/WEB-INF/lib/properties/root.properties";
            }
            else if (properties.equals("client")) {
                path = "/WEB-INF/lib/properties/client.properties";
            }
            else {
                path = "/WEB-INF/lib/properties/data-entry.properties";
            }

            // Get the specific properties file
            ServletContext context = getServletContext();
            Properties prop = null;
            InputStream fis;
            try {
                prop = new Properties();
                fis = context.getResourceAsStream(path);

                if (fis != null) prop.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get the username and password from the properties file
            String user = prop.getProperty("user");
            String pass = prop.getProperty("pass");

            // If the user put the correct credential, go to a specific jsp based on the properties type
            if (user.equals(username) && pass.equals(password)) {
                location = properties + ".jsp";
            }
            // Display a message that the password or username is wrong
            else {
                execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">Error: Invalid username or password, please try again</p></div>";
                session.setAttribute("display", execute);
            }
        }
        // Display a message that one of the input fields is empty
        else {
            execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">Error: One of the input fields is empty</p></div>";
            session.setAttribute("display", execute);
        }
        return location;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
}
