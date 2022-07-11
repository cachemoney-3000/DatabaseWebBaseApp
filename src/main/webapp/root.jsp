<%@ page import="java.io.PrintWriter" %><%--
Name: Joshua Samontanez
Course: CNT 4714 – Summer 2022 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 4, 2022
--%>
<%
    String textBox = (String) session.getAttribute("textBox");
    String result = (String) session.getAttribute("result");
    String execute = (String) session.getAttribute("execute");

    if(result == null){
        result = "";
    }
    if(textBox == null){
        textBox = "";
    }
    if(execute == null){
        execute = "";
    }
%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Root-user</title>
    <link rel="stylesheet" href="styles/rootStyle.css">
    <title>Root user</title>
</head>
<body>
<html>
<div>
    <h1 style="color:#fecd05;">Welcome to the Enterprise
        </br>Database System
    </h1>
    <h2 style="color:#e2dce4;">A Servlet/JSP-based multi-tiered enterprise application using Tomcat container</h2>
</div>

<div class = "Hbox">
    <div class = "container">
        <div class = "connectionContainer">
            <label class = "connectionText">You are connected to the Project 3 Enterprise System Database as a data-entry user</label>
        </div>
        <form action="root" method="get" class = "button-inline">
            <input type = "submit" value = "Logout" name="logoutButton" id="logoutButton"  class = "logoutButton">
        </form>
    </div>
</div>

<body style="background-color: #222b45;">
<div class = "Hbox">
    <form action="root" method="post">
        <div class>
            <br/>
            <textarea type = "text" name="textBox" id="textBox" class = "textarea" placeholder = "Type your SQL commands here" rows="4" cols="50"><%= textBox %></textarea>
        </div>

        <div class = "container">
            <input type = "submit" value = "Reset Table" name="button_clicked" id="resetButton"  class = "resetButton">
            <input type = "submit" value = "Clear"  name="button_clicked" id="clearButton"  class = "clearButton">
            <input type = "submit" value = "Execute"  name="button_clicked" id="executeButton" class = "executeButton">
        </div>
    </form>
</div>

<div>
    <%= execute %>
</div>

<div class = "tableResult">
    <%-- jsp statement with out sql response--%>
    <%= result %>
</div>


<script  src="./sortable.js"></script>
</body>
</html>

