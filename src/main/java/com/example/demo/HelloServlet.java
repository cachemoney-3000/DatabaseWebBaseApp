package com.example.demo;

import java.io.*;
import java.sql.*;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Vector;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private Connection connection;
    private Statement statement;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project3", "client", "client");
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {



        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String textBox = request.getParameter("textBox");
        String textBoxLowerCase = textBox.toLowerCase();
        String result = null;

        //check to see if it is a select statement
        if (textBoxLowerCase.contains("select")) {
            try {
                result = doSelectQuery(textBoxLowerCase);
            } catch (SQLException e) {
                result = "<span>" + e.getMessage() + "</span>";

                e.printStackTrace();
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("result", result);
        session.setAttribute("textBox", textBox);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);


        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // execute a select query and create table html with resultset
    public String doSelectQuery(String textBox) throws SQLException {
        String result;
        // run sql command
        ResultSet table = statement.executeQuery(textBox);
        // process query results

        ResultSetMetaData metaData = table.getMetaData();
        // table columns html
        int numOfColumns = metaData.getColumnCount();
        // html table opening html
        String tableOpeningHTML = "<table class='table-sortable'>";
        // table html columns
        String tableColumnsHTML = "<thead class='thead-dark'><tr>";
        for (int i = 1; i <= numOfColumns; i++) {
            tableColumnsHTML += "<th scope='col'>" + metaData.getColumnName(i).toUpperCase() + "</th>";
        }

        tableColumnsHTML += "</tr></thead>"; // close the html tale column element

        // table html body/rows
        String tableBodyHTML = "<tbody>";
        // get row info
        while (table.next()) {
            tableBodyHTML += "<tr>";
            for (int i = 1; i <= numOfColumns; i++) {
                // if first element
                if (i == 1)
                    tableBodyHTML += "<td scope'row'>" + table.getString(i) + "</th>";
                else
                    tableBodyHTML += "<td>" + table.getString(i) + "</th>";
            }
            tableBodyHTML += "</tr>";
        }

        tableBodyHTML += "</tbody>";

        // closing html
        String tableClosingHTML = "</table>";
        result = tableOpeningHTML + tableColumnsHTML + tableBodyHTML + tableClosingHTML;

        return result;
    }
}