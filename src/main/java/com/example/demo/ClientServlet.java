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

public class ClientServlet extends HttpServlet {
    private Connection connection;
    private Statement statement;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project3", "root", "Owaako29!");
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
        String result;

        HttpSession session = request.getSession();
        // EXECUTION (FAIL/GOOD) TEXT
        String execute = "<div class = \"executionContainer\"><p class = \"executionText\">Awaiting command...</p></div>";

        String buttonClicked = request.getParameter("button_clicked");

        String text = "";
        if (buttonClicked.equals("Execute")) {
            //check to see if it is a select statement
            if (textBoxLowerCase.contains("select")) {
                try {
                    result = doSelectQuery(textBoxLowerCase);
                    session.setAttribute("result", result);
                    execute = "<div class = \"executionContainerGood\"><p class = \"executionText\">Command Successful</p></div>";
                } catch (SQLException e) {
                    execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">" + e.getMessage() + "</p></div>";
                    e.printStackTrace();
                }

                session.setAttribute("execute", execute);
            }
            else if (textBoxLowerCase.equals("")) {
                execute = "<div class = \"executionContainerGood\"><p class = \"executionText\">Empty command</p></div>";
                session.setAttribute("execute", execute);
            }
            // UPDATE STATEMENTS
            else {
                //execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">Invalid Command</p></div>";
                //session.setAttribute("execute", execute);

                try {
                    execute = doUpdateQuery(textBoxLowerCase);
                    session.setAttribute("execute", execute);

                    //execute = "<div class = \"executionContainerGood\"><p class = \"executionText\">Command Successful</p></div>";

                }catch(SQLException e) {
                    execute = "<div class = \"executionContainerBad\"><p class = \"executionText\">" + e.getMessage() + "</p></div>";
                    e.printStackTrace();
                }

                session.setAttribute("execute", execute);
            }

            text = textBox;
            session.setAttribute("textBox", text);
        }
        else if (buttonClicked.equals("Clear")) {
            text = "";
            session.setAttribute("textBox", text);
        }
        else if (buttonClicked.equals("Reset Table")) {
            result = null;
            session.setAttribute("result", result);

            execute = "<div class = \"executionContainer\"><p class = \"executionText\">Table cleared...</p></div>";
            session.setAttribute("execute", execute);
        }

        System.out.println(buttonClicked);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);

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

    private String doUpdateQuery(String textBoxLowerCase) throws SQLException {
        StringBuilder result = new StringBuilder();
        int numOfRowsUpdated = 0;

        //get number of shipment with quantity >= 100 before update/insert
        ResultSet beforeQuantityCheck = statement.executeQuery("select COUNT(*) from shipments where quantity >= 100");
        beforeQuantityCheck.next();
        int numOfShipmentsWithQuantityGreaterThan100Before = beforeQuantityCheck.getInt(1);


        statement.execute("SET FOREIGN_KEY_CHECKS = 0;");



        //create temp table for the case of updating suppliers status's
        statement.executeUpdate("create table shipmentsBeforeUpdate like shipments");
        //copy table over to new temp table
        statement.executeUpdate("insert into shipmentsBeforeUpdate select * from shipments");

        result.append("<div class = \"executionContainer\"><p class = \"executionText\">");
        //execute update
        numOfRowsUpdated = statement.executeUpdate(textBoxLowerCase);
        result.append("The statement executed succesfully.</br>").append(numOfRowsUpdated).append(" row(s) affected");

        //get number of shipment with quantity >= 100 before update/insert
        ResultSet afterQuantityCheck = statement.executeQuery("select COUNT(*) from shipments where quantity >= 100");
        afterQuantityCheck.next();
        int numOfShipmentsWithQuantityGreaterThan100After = afterQuantityCheck.getInt(1);

        result.append("</br>").append(numOfShipmentsWithQuantityGreaterThan100Before).append(" < ").append(numOfShipmentsWithQuantityGreaterThan100After);

        //update the status of suppliers if shipment quantity is > 100
        if(numOfShipmentsWithQuantityGreaterThan100Before < numOfShipmentsWithQuantityGreaterThan100After) {
            //increase suppliers status by 5
            //handle updates into shipments by using a left join with shipments and temp table
            int numberOfRowsAffectedAfterIncrementBy5 = statement.executeUpdate("update suppliers set status = status + 5 where snum in ( select distinct snum from shipments left join shipmentsBeforeUpdate using (snum, pnum, jnum, quantity) where shipmentsBeforeUpdate.snum is null)");
            result.append("</br>Business Logic Detected! - Updating Supplier Status");
            result.append("</br>Business Logic Updated ").append(numberOfRowsAffectedAfterIncrementBy5).append(" Supplier(s) status marks");
        }


        statement.executeUpdate("drop table shipmentsBeforeUpdate");

        result.append("</p>");
        return result.toString();
    }
}