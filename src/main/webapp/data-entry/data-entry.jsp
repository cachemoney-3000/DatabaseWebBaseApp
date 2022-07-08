<%--
  Created by IntelliJ IDEA.
  User: samon
  Date: 7/8/2022
  Time: 3:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<div class = "connectionContainer">
  <p class = "connectionText">You are connected to the Project 3 Enterprise System Database as a user-level user</p>
</div>

<div class = "Hbox">
  <div class = "forms">
    <form class="form-inline">
      <label for = "snum">SNUM</label>
      <input name="snum" id="snum" placeholder="Enter snum" name="email">
    </form>

    <form class="form-inline">
      <label for = "pnum">PNUM</label>
      <input name="pnum" id="pnum" placeholder="Enter pnum">
    </form>

    <form class="form-inline">
      <label for = "jnum">JNUM</label>
      <input name="jnum" id="jnum" placeholder="Enter jnum">
    </form>

    <form class="form-inline">
      <label for = "quantity">QUANTITY</label>
      <input name="quantity" id="quantity" placeholder="Enter quantity">
    </form>

    <div class = "container">
      <button name="clearButton" id="clearButton"  class = "clearButton">Clear</button>
      <button name="executeButton" id="executeButton" class = "executeButton">Execute</button>
    </div>
  </div>
</div>

<div class = "executionContainer">
  <p class = "executionText">Command executed successfully
    </br> 999 row(s) affected
    </br> Business logic detected! - Updating supplier status
    </br> Business logic updated 999 supplier(s) status marks
  </p>
</div>
</body>
</html>

