<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Order.Order"%>
<%@page import="Model.Order.OrderItem"%>
<%@page import="Model.Items.Unit"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<html>
<head>
    <title>Your Orders</title>
    <link rel="stylesheet" type="text/css" href="../css/IoTBay.css">
    <link rel="icon" type="image/png" href="../images/CJ_MAXX.png">
</head>

<body>

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

<div class="orderText">
<br>
<h1>Your Orders</h1>

<%
    Integer userIdObj = (Integer) session.getAttribute("userId");
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
    List<Order> orders = null;
    boolean isGuest = (userIdObj == null);

    if (!isGuest) {
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
            int userId = userIdObj;
            User user = dbm.getUserById(userId);
            orders = dbm.getOrdersByUserId(userId, true); 
        }
        catch (Exception e) {
%>
            <p style="color:red;">An error occurred while loading your orders. Please try again later.</p>
<%
            return;
        }
    } else {
        @SuppressWarnings("unchecked")
        List<Order> guestOrders = (List<Order>) session.getAttribute("guestOrders");
        orders = guestOrders;
    }

    if (orders != null && !orders.isEmpty()) {
        for (Order order : orders) {
%>
                <div class="order-container" style="border: 1px solid #ccc; margin-bottom: 20px; padding: 15px;">
                    <h2>Order ID: <%= order.getOrderID() %></h2>
                    <p><strong>Date:</strong> <%= sdf.format(order.getOrderDate()) %></p>
                    <p><strong>Status:</strong> <%= order.getOrderStatus().toString() %></p>
                    <p><strong>Shipping Address:</strong> <%= order.getShippingAddress() %></p>
                    
                    <h3>Items:</h3>
                    <table border="1" style="width:100%; border-collapse: collapse;">
                        <thead>
                            <tr>
                                <th>Item Name</th>
                                <th>Quantity</th>
                                <th>Price per Unit (at purchase)</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
<%
                        double orderTotal = 0;
                        if (order.getOrderItems() != null) {
                            for (OrderItem item : order.getOrderItems()) {
                                Unit unit = item.getUnit();
                                ItemType itemType = unit.getItemType();
                                String itemName = itemType.getName();
                                int quantity = item.getQuantity();
                                double priceAtPurchase = item.getPriceAtPurchase() / 100.0;
                                double subtotal = quantity * priceAtPurchase;
                                orderTotal += subtotal;
%>
                            <tr>
                                <td><%= itemName %></td>
                                <td style="text-align:center;"><%= quantity %></td>
                                <td style="text-align:right;">$<%= String.format("%.2f", priceAtPurchase) %></td>
                                <td style="text-align:right;">$<%= String.format("%.2f", subtotal) %></td>
                            </tr>
<%
                            }
                        }
%>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" style="text-align:right; font-weight:bold;">Order Total:</td>
                                <td style="text-align:right; font-weight:bold;">$<%= String.format("%.2f", orderTotal) %></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
<%
            }
        } else {
%>
            <p>You have no orders yet.</p>
<%
        }
%>
<br/>
<p>
    Click <a href="../userHome.jsp">here</a> to return to your home page.<br/>
</p>

</div>
</body>
</html>