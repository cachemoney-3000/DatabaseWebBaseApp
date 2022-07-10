<%--
Name: Joshua Samontanez
Course: CNT 4714 – Summer 2022 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: August 4, 2022
--%>
<%
    String execute = (String) session.getAttribute("execute");

    if(execute == null){
        execute = "";
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Database Login</title>
    <link rel="stylesheet" href="./login/style.css">

</head>
<body>
<!-- partial:index.partial.html -->
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>DataInput</title>
    <link rel="stylesheet" href="./style.css">
</head>

<body style="background-color: #222b45;">
<div>
    <h1 style="color:#fecd05;">Welcome to the Enterprise
        </br>Database System
    </h1>
    <h2 style="color:#e2dce4;">A Servlet/JSP-based multi-tiered enterprise application using Tomcat container</h2>
</div>

<div class = "Hbox">
    <form class = "forms" action="login" method="get">
        <label class = "logInLabel">Database Login:</label>
        <div class = "form-inline">
            <input name="username" id="username" placeholder="Enter username">
        </div>
        <div class = "form-inline">
            <input name="password" id="password" placeholder="Enter password">
        </div>
        <div class = "form-inline">
            <select  class = "dropbtn" name="properties" id="properties" value = "Execute">
                <option  disabled selected>Select a properties file</option>
                <option value="root">Root</option>
                <option value="client">Client</option>
                <option value="data">Data-entry</option>
            </select>
        </div>

        <div class = "container">
            <input type = "submit" name="login" id="login" class = "executeButton" value = "Login">
        </div>
    </form>
</div>

</body>
</html>
<!-- partial -->

</body>
</html>
