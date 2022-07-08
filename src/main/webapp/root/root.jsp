<%--
  Created by IntelliJ IDEA.
  User: samon
  Date: 7/8/2022
  Time: 3:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Root-user</title>
  <link rel="stylesheet" href="./style.css">
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

<div class = "connectionContainer">
  <p class = "connectionText">You are connected to the Project 3 Enterprise System Database as a root-level user</p>
</div>

<body style="background-color: #222b45;">
<div class = "Hbox">
  <div class>
    <textarea name="textBox" id="textBox" class = "textarea" placeholder = "Type your SQL commands here" rows="4" cols="50"></textarea>
  </div>

  <div class = "container">
    <button name="resetButton" id="resetButton"  class = "resetButton">Reset Table</button>
    <button name="clearButton" id="clearButton"  class = "clearButton">Clear</button>
    <button name="executeButton" id="executeButton" class = "executeButton">Execute</button>
  </div>
</div>

<div class = "executionContainer">
  <p class = "executionText">Command executed successfully
    </br> 999 row(s) affected
    </br> Business logic detected! - Updating supplier status
    </br> Business logic updated 999 supplier(s) status marks
  </p>
</div>

<div class = "tableResult">
  <table class="content-table">
    <thead>
    <tr>
      <th>Rank</th>
      <th>Name</th>
      <th>Points</th>
      <th>Team</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>1</td>
      <td>Domenic</td>
      <td>88,110</td>
      <td>dcode</td>
    </tr>
    <tr>
      <td>2</td>
      <td>Sally</td>
      <td>72,400</td>
      <td>Students</td>
    </tr>
    <tr>
      <td>3</td>
      <td>Nick</td>
      <td>52,300</td>
      <td>dcode</td>
    </tr>
    <tr>
      <td>3</td>
      <td>Nick</td>
      <td>52,300</td>
      <td>dcode</td>
    </tr>
    </tbody>
  </table>
</div>
</body>
</html>

