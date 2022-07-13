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
  <title>DataInput</title>
  <link rel="stylesheet" href="styles/dataStyle.css">
</head>

<body style="background-color: #222b45;">
<div>
  <h1 style="color:#fecd05;">Welcome to the Enterprise
    </br>Database System
  </h1>
  <h2 style="color:#e2dce4;">A Servlet/JSP-based multi-tiered enterprise application using Tomcat container</h2>
</div>

<div class = "Vbox">
  <div class = "container">
    <div class = "connectionContainer">
      <label class = "connectionText">You are connected to the Project 3 Enterprise System Database as a data-entry user</label>
    </div>
    <form action="data-entry" method="post" class="button-inline">
      <input type = "submit" value = "Logout" name="logoutButton" id="logoutButton"  class = "logoutButton">
    </form>
  </div>
</div>

<div class = "Hbox">
  <form class = "forms" action="data-entry", method="get">
    <div class = "form-inline">
      <label for = "snum">SNUM</label>
      <input name="snum" id="snum" placeholder="Enter snum" name="email" required>
    </div>
    <div class = "form-inline">
      <label for = "pnum">PNUM</label>
      <input name="pnum" id="pnum" placeholder="Enter pnum" required>
    </div>
    <div class = "form-inline">
      <label for = "jnum">JNUM</label>
      <input name="jnum" id="jnum" placeholder="Enter jnum" required>
    </div>
    <div class = "form-inline">
      <label for = "quantity">QUANTITY</label>
      <input name="quantity" id="quantity" placeholder="Enter quantity" required>
    </div>

    <div class = "container">
      <input type = "submit" name="button_clicked" id="clearButton"  class = "clearButton" value = "Clear">
      <input type = "submit" name="button_clicked" id="executeButton" class = "executeButton" value = "Execute">
    </div>
  </form>
</div>

<div>
  <%= execute %>
</div>
</body>
</html>