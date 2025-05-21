<html>
<head>
    <title>Checkout</title>
</head>
<h1>Checkout</h1>
<%
    String errorMessage = request.getParameter("error");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
    <p style="color:red; font-weight:bold;"><%= errorMessage.replace("+", " ") %></p>
<%
    }
%>

<body>
<h2>Delivery method:</h2>
<form>
    <select id="deliveryMethod" name="deliveryMethod" required>
        <option value="">Select</option>
        <option value="Delivery">Delivery</option>
        <option value="Click&Collect">Click & Collect</option>
        <option value="Ship">Ship to collection point</option>
        <input type="hidden" name="selectedDelivery" value="true">
        <input type="submit" value="set">
    </select>
</form>

</body>

<%
    String deliveryMethod = request.getParameter("deliveryMethod");
    String selectedDelivery = request.getParameter("selectedDelivery");

    if (selectedDelivery != null && selectedDelivery.equals("true")){
        if (deliveryMethod.equals("Delivery")){
%>
    <h2>Shipping Address</h2>
    <form method="post" action="/order">
        <input type="hidden" name="deliveryType" value="delivery">
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
        <input type="submit" value="Submit">
    </form>
<%
        } else if (deliveryMethod.equals("Click&Collect")){
%>
<h2>Click & Collect</h2>
<form method="post" action="/order">
    <input type="hidden" name="deliveryType" value="click">
    <label><input type="radio" name="selectedStore" value="Central Storefront" required > Central Storefront </label><br>
    <label><input type="radio" name="selectedStore" value="Lidcombe Storefront" > Lidcombe Storefront </label><br>
    <label><input type="radio" name="selectedStore" value="Bankstown Storefront" > Bankstown Storefront </label><br>
    <input type="submit" value="Submit">
</form>
<%
        } else if (deliveryMethod.equals("Ship")){
%>
<h2>Select a collection point</h2>
<form method="post" action="/order">
    <input type="hidden" name="deliveryType" value="collection">
    <select id="collectionPoint" name="collectionPoint" required>
        <option value="">Select</option>
        <option value="AustraliaPost">Australia Post Parcel Lockers and Parcel Collect</option>
        <option value="ParcelPoint">ParcelPoint</option>
        <option value="HUBBED">HUBBED</option>
    </select><br>
    <input type="submit" value="Submit">
</form>
<%
        }
    }
%>
Click <a href="../userHome.jsp">here </a>to proceed to the main page. <br/>
Click <a href="basket.jsp">here </a>to proceed to the basket. <br/>
</html>
