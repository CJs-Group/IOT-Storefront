<%@page import="Model.Items.ItemType"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>

<html>
<head>
  <title>Stock Management</title>
</head>
<body>
<h1>Total Stock</h1>

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
      // Updated loop to iterate over List<ItemType>
      for (ItemType item : items) {
        int quantity = item.getQuantity();
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
</body>
</html>