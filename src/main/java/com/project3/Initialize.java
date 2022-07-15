/* Name: Joshua Samontanez
Course: CNT 4714 – Summer 2022 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 4, 2022
*/

package com.project3;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Initialize {
    public Statement initializeServlet(ServletConfig config, ServletContext context, String filepath) throws SQLException {
        Properties prop = null;
        InputStream fis;
        try {
            prop = new Properties();
            fis = context.getResourceAsStream(filepath);
            if (fis != null) prop.load(fis);
            else System.out.println("Cannot find the properties file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the data from the properties file
        String user = prop.getProperty("user");
        String pass = prop.getProperty("pass");
        String url = prop.getProperty("url");
        String driver = prop.getProperty("driver");
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Avoid leak if an exception was thrown in createStatement
            if (statement == null) {
                connection.close();
            }
        }


        return statement;
    }
}
