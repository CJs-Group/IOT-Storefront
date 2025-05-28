<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Order.Order"%>
<%@page import="Model.Order.OrderItem"%>
<%@page import="Model.Items.Unit"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<html>
<head>
    <title>Order Receipt</title>
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

<div class="mainText">
<br>
<h1>Order Confirmation</h1>

<%
    String successMessage = request.getParameter("success");
    if (successMessage != null && !successMessage.isEmpty()) {
%>
    <p style="color:green; font-weight:bold;"><%= successMessage %></p>
<%
    }

    String orderIdStr = request.getParameter("orderId");
    Order order = null;
    DBManager dbm = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
    try (DBConnector dbc = new DBConnector()) {
        dbm = new DBManager(dbc.openConnection());
        int orderId = Integer.parseInt(orderIdStr);
        Object userIdObj = session.getAttribute("userId");

        if (userIdObj != null) {
            order = dbm.getOrderById(orderId, true);
        }
        else {
            List<Order> guestOrders = (List<Order>) session.getAttribute("guestOrders");
            order = guestOrders.stream()
                .filter(o -> o.getOrderID() == orderId)
                .findFirst()
                .orElse(null);
        }
%>
                <h2>Thank you for your purchase!</h2>
                <p><strong>Order ID:</strong> <%= order.getOrderID() %></p>
                <p><strong>Order Date:</strong> <%= sdf.format(order.getOrderDate()) %></p>
                <p><strong>Order Status:</strong> <%= order.getOrderStatus().toString() %></p>
                
                <h3>Delivery Details:</h3>
                <p><%= order.getShippingAddress() %></p>

                <h3>Order Summary:</h3>
                <table border="1" style="width:50%; border-collapse: collapse;">
                    <thead>
                        <tr>
                            <th>Item [Unit #]</th>
                            <th>Price per Unit</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
<%
                    double grandTotal = 0;
                    for (OrderItem item : order.getOrderItems()) {
                        Unit unit = item.getUnit();
                        ItemType itemType = unit.getItemType();
                        String itemName = itemType.getName();
                        int quantity = item.getQuantity();
                        double priceAtPurchase = item.getPriceAtPurchase() / 100.0;
                        double subtotal = quantity * priceAtPurchase;
                        grandTotal += subtotal;
%>
                        <tr>
                            <td><%= itemName + " [ " + unit.getUnitID() + " ]" %></td>
                            <td style="text-align:right;">$<%= String.format("%.2f", priceAtPurchase) %></td>
                            <td style="text-align:right;">$<%= String.format("%.2f", subtotal) %></td>
                        </tr>
<%
                    }
%>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="2" style="text-align:right; font-weight:bold;">Grand Total:</td>
                            <td style="text-align:right; font-weight:bold;">$<%= String.format("%.2f", grandTotal) %></td>
                        </tr>
                    </tfoot>
                </table>
<%
    } catch (Exception e) {
%>
            <p style="color:red;">An unexpected error occurred while displaying your receipt. Please contact support.</p>
<%
    }
%>
<p>
    Click <a href="index.jsp">here</a> to return to the main page. <br/>
    Click <a href="orders.jsp">here</a> to view all your orders. <br/>
</p>

</div>
</body>
</html>