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
    <title>Order Edit</title>
    <link rel="stylesheet" type="text/css" href="../css/IoTBay.css">
    <link rel="icon" type="image/png" href="../images/CJ_MAXX.png">
</head>
<body>
<jsp:include page="includes/topbar.jsp" />

<div class="mainText">
<h1>Order Edit</h1>

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
        order = dbm.getOrderById(orderId, true);
%>
                <h3><p><strong>Order ID:</strong> <%= order.getOrderID() %></p></h3>
                <h3>Delivery Details:</h3>
                <p><%= order.getShippingAddress() %></p>

                <h3>New Delivery Method:</h3>
        <form>
            <select id="deliveryMethod" name="deliveryMethod" required>
                <option value="">Select</option>
                <option value="Delivery">Delivery</option>
                <option value="Click&Collect">Click & Collect</option>
                <option value="Ship">Ship to collection point</option>
                <input type="hidden" name="selectedDelivery" value="true">
                <input type="hidden" name="orderId" value="<%= orderId %>">
                <input type="submit" value="set">
            </select>
        </form>
<%
    String deliveryMethod = request.getParameter("deliveryMethod");
    String selectedDelivery = request.getParameter("selectedDelivery");

    if (selectedDelivery != null && selectedDelivery.equals("true")){
        if (deliveryMethod.equals("Delivery")){
%>
    <h2>Shipping Address</h2>
    <form method="post" action="/order">
        <input type="hidden" name="deliveryType" value="delivery">
        <input type="hidden" name="orderId" value=<%= orderId %>>
        <label>Country/Region: </label><br>
        <input type="text" id="country" name="country" required><br>
        <label>First Name: </label><br>
        <input type="text" id ="firstName" name="firstName" required><br>
        <label>Last Name: </label><br>
        <input type="text" id="lastName" name="lastName" required><br>
        <label>Address: </label><br>
        <input type="text" id="address" name="address" required ><br>
        <label>City: </label><br>
        <input type="text" id="city" name="city" required><br>
        <label>State/Territory: </label><br>
        <select id="state" name="state" required>
            <option value="">Select</option>
            <option value="NSW">NSW</option>
            <option value="QLD">QLD</option>
            <option value="VIC">VIC</option>
            <option value="ACT">ACT</option>
            <option value="SA">SA</option>
            <option value="NT">NT</option>
            <option value="WA">WA</option>
        </select><br>
        <input type="submit" name="action" value="Change Delivery Method">
    </form>
<%
        } else if (deliveryMethod.equals("Click&Collect")){
%>
<h2>Click & Collect</h2>
<form method="post" action="/order">
    <input type="hidden" name="deliveryType" value="click">
    <input type="hidden" name="orderId" value=<%= orderId %>>
    <label><input type="radio" name="selectedStore" value="Central Storefront" required > Central Storefront </label><br>
    <label><input type="radio" name="selectedStore" value="Lidcombe Storefront" > Lidcombe Storefront </label><br>
    <label><input type="radio" name="selectedStore" value="Bankstown Storefront" > Bankstown Storefront </label><br>
    <input type="submit" name="action" value="Change Delivery Method">
</form>
<%
        } else if (deliveryMethod.equals("Ship")){
%>
<h2>Select a collection point</h2>
<form method="post" action="/order">
    <input type="hidden" name="orderId" value=<%= orderId %>>
    <input type="hidden" name="deliveryType" value="collection">
    <select id="collectionPoint" name="collectionPoint" required>
        <option value="">Select</option>
        <option value="AustraliaPost">Australia Post Parcel Lockers and Parcel Collect</option>
        <option value="ParcelPoint">ParcelPoint</option>
        <option value="HUBBED">HUBBED</option>
    </select><br>
    <input type="submit" name="action" value="Change Delivery Method">
</form>
<%
        }
    }
%>

                <h3>Order Items:</h3>
                <table border="1" style="width:80%; border-collapse: collapse;">
                    <thead>
                        <tr>
                            <th>Item Name</th>
                            <th>Price Per Unit</th>
                            <th>Subtotal</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
<%
                    int showQuantity = 0;
                    double grandTotal = 0;
                    Order currOrder = dbm.getOrderById(orderId, true);
                    List<OrderItem> orderItems = currOrder.getOrderItems();
                    for (OrderItem item : orderItems) {
                        Unit unit = item.getUnit();
                        ItemType itemType = unit.getItemType();
                        String itemName = itemType.getName();
                        int quantity = item.getQuantity();
                        double priceAtPurchase = item.getPriceAtPurchase() / 100.0;
                        double subtotal = quantity * priceAtPurchase;
                        grandTotal += subtotal;
%>
                            <tr>
                                <td><%= itemName %></td>
                                <td style="text-align:right;">$<%= String.format("%.2f", priceAtPurchase) %></td>
                                <td style="text-align:right;">$<%= String.format("%.2f", subtotal) %></td>
                                <td style="text-align:center;">
                                    <form method="post" action="../order" style="margin:0;">
                                        <input type="hidden" name="action" value="removeItem" />
                                        <input type="hidden" name="orderId" value="<%= order.getOrderID() %>" />
                                        <input type="hidden" name="unitId" value="<%= item.getUnit().getUnitID() %>" />
                                        <button type="submit">Remove</button>
                                    </form>
                                </td>
                            </tr>
<%
                    }
%>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3" style="text-align:right; font-weight:bold;">Grand Total:</td>
                            <td style="text-align:right; font-weight:bold;">$<%= String.format("%.2f", grandTotal) %></td>
                        </tr>
                    </tfoot>
                </table>
<%
    } catch (Exception e) {
%>
    <h2>Error</h2> 
<%
        // Log the error for debugging
        e.printStackTrace();
%>
            <p style="color:red;">An unexpected error occurred while displaying your receipt. Please contact support.</p>
<%
    }
%>

<br/>
<p>
    <a href="orders.jsp"><button type="button">Back to Orders</button></a>
</p>
</div>
</body>
</html>