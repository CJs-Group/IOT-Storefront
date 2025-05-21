<%@page import="Model.Users.User"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<html>
<head>
    <title>BASKET</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%
Map<Integer, Integer> items = new HashMap<>();
try (DBConnector dbc = new DBConnector()) {
    DBManager dbm = new DBManager(dbc.openConnection());
    User user = (User) session.getAttribute("user");
    if (user == null) {
      items = new HashMap<Integer, Integer>();    
    } else {
      items = dbm.getBasketItemIds(user.getUserID());
    }
      
%>
    <h1>BASKET</h1>
<%
      if (items.isEmpty()) {
%>
    <p>Your basket is empty.</p>
<%    
      } else {
%>
    <label>Your Basket contains:</label><br>
<%
      
      for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
        String itemName = dbm.getItemById(entry.getKey()).getName();
%>
    <label>Item: <%= itemName %>, Quantity: <%= entry.getValue() %></label>
    <form method="post" action="../updateBasket">
        <input type="hidden" name="itemId" value="<%= entry.getKey() %>">
        <button type="submit" name="action" value="remove">Remove</button>
        <button type="submit" name="action" value="+1">+1</button>
        <button type="submit" name="action" value="-1">-1</button>
    </form>
<%
        }
  }
} catch (Exception e) {
    out.println("Error: " + e.getMessage());
    // Optionally log the stack trace
}
  if (!items.isEmpty()) {
%>
  Click <a href="checkout.jsp">here </a>to proceed for checkout. <br/>
<%
  }
%>
  Click <a href="../userHome.jsp">here </a>to proceed to the main page. <br/>
</body>
</html>