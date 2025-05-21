<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="java.util.List"%>
<%@page import="Model.Items.ItemType"%>
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
    List<Integer> orders = dbm.getOrderIdsForUser(user.getUserID());
    if (!orders.isEmpty()) {
        for (Integer orderId : orders) {
%>
            <h2>Order ID: <%= orderId %></h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                <% 
                    List<Integer> itemIds = dbm.getOrderItemIds(orderId);
                    int orderTotal = 0;
                    for (int itemId : itemIds) {
                        ItemType item = dbm.getItemById(itemId);
                        String itemName = item.getName();
                        int itemPrice = item.getPrice();
                        int itemQuantity = dbm.getOrderItemQuantity(orderId, itemId);
                        int subtotal = itemPrice * itemQuantity;
                        orderTotal += subtotal;
                %>
                    <tr>
                        <td><%= itemName %></td>
                        <td><%= itemPrice %></td>
                        <td><%= itemQuantity %></td>
                        <td><%= subtotal %></td>
                    </tr>
                <% 
                    }
                %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3" style="text-align:right;"><strong>Total:</strong></td>
                        <td><strong><%= orderTotal %></strong></td>
                    </tr>
                </tfoot>
            </table>
            <br/>
<%      }
    } else {
%>
        <p>You have no orders yet.</p>
<%
    }
} catch (Exception e) {
    out.println("<div style='color:red;'>An error occurred while loading your orders. Please try again later.</div>");
    // e.printStackTrace(); // Optionally log the stack trace for debugging
}
%>
Click <a href="../userHome.jsp">here</a> to return to your home page.<br/>
</body>
</html>
