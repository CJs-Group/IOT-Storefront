<%@page import="Model.Items.ItemType"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>

<html>
<head>
  <title>Stock Management</title>
  <link rel="stylesheet" type="text/css" href="../css/IoTBay.css">
  <link rel="icon" type="image/png" href="../images/CJ_MAXX.png">
</head>

<body>

<style>
  body {
    overflow: auto;
  }
</style>

<div class="topBar"></div>
<img src="../images/CJ_MAXX.png" class="logo">
<div class="searchBarPos"><input type="text" placeholder="Search.." class="searchBar"></div>

<div class="buttonContainer">
    <a href="login.jsp" class="registerContainer">
        <img src="../images/login.png" class="registerIcon">
        <p class="registerText">Login</p>
    </a>
    
    <a href="register.jsp" class="registerContainer">
        <img src="../images/user.png" class="registerIcon">
        <p class="registerText">Register</p>
    </a>
</div>

<div class="leftBar"></div>
<div class="rightBar"></div>

<div class="registerPageText">
<h1>Total Stock</h1>
<%
  String error = request.getParameter("error");
  if (error != null && !error.trim().isEmpty()) {
%>
    <div style="color: red;">
        <strong>Error:</strong> <%= error %>
    </div>
<%
  }
%>

<%
  Connection conn = null;
  DBManager dbm = null;
  try {
    DBConnector dbConnector = new DBConnector();
    conn = dbConnector.openConnection();
    dbm = new DBManager(conn);

    List<ItemType> items = dbm.getAllItemTypes();
  
    if (items.size() == 0) {
%>
      <a style="float:left">There are no items listed in stock.</a><br>
      <a style="float:left">Please add items to stock.</a><br>
      <%-- <a style="float:left" href="addItem.jsp">Add an item.</a><br> --%>
<%
    } else {
%>
      <%-- <a style="float:left" href="addItem.jsp">Add an item.</a><br> --%>
      <label> Your Stock contains: </label><br>
<%
      for (ItemType item : items) {
        int quantity = dbm.getItemQuantity(item.getItemID());
%>
        <label>Item: <%= item.getName() %>, Quantity: <%= quantity %></label>
        <form method="post" action="../updateStock">
          <input type="hidden" name="itemId" value="<%= item.getItemID() %>">
          <button type="submit" name="action" value="remove">Remove</button>
          <button type="submit" name="action" value="+1">+1</button>
          <button type="submit" name="action" value="-1">-1</button>
        </form>
<%
      }
    }
%>
    Click <a href="../userHome.jsp">here </a>to proceed to the main page. <br/>
<%
  } catch (Exception e) {
    out.println("<!-- An error occurred: " + e.getMessage() + " -->");
    e.printStackTrace(new java.io.PrintWriter(out));
  }
%>
</div>
</body>
</html>