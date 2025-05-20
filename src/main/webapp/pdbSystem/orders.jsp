<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.DB"%>
<%@page import="Model.Users.User"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<html>
<head>
    <title>Your Orders</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h1>Your Orders</h1>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("../login.jsp");
    return;
}
try (DBConnector dbc = new DBConnector()) {
    DBManager dbm = new DBManager(dbc.openConnection());
    // Query all orders for this user
    ResultSet rs = dbc.openConnection().createStatement().executeQuery(
        "SELECT orderID, itemID, quantity, totalPrice FROM Orders WHERE userID = " + user.getUserID() + " ORDER BY orderID DESC"
    );
    Map<Integer, java.util.List<Map<String, Object>>> orders = new HashMap<>();
    while (rs.next()) {
        int orderId = rs.getInt("orderID");
        Map<String, Object> item = new HashMap<>();
        int itemId = rs.getInt("itemID");
        item.put("itemName", DB.getItemById(itemId).getName());
        item.put("quantity", rs.getInt("quantity"));
        item.put("totalPrice", rs.getDouble("totalPrice"));
        orders.computeIfAbsent(orderId, k -> new java.util.ArrayList<>()).add(item);
    }
    if (orders.isEmpty()) {
%>
    <p>You have no orders yet.</p>
<%
    } else {
        for (Integer orderId : orders.keySet()) {
%>
    <h2>Order ID: <%= orderId %></h2>
    <table border="1">
        <tr><th>Item</th><th>Quantity</th><th>Total Price</th></tr>
        <% for (Map<String, Object> item : orders.get(orderId)) { %>
        <tr>
            <td><%= item.get("itemName") %></td>
            <td><%= item.get("quantity") %></td>
            <td>$<%= String.format("%.2f", (Double)item.get("totalPrice")) %></td>
        </tr>
        <% } %>
    </table>
    <br/>
<%      }
    }
}
%>
Click <a href="../userHome.jsp">here</a> to return to your home page.<br/>
</body>
</html>
