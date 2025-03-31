<html>
<h1>Checkout</h1>

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
    <form action="welcome.jsp" method="post">
        <label>Country/Region: </label><br>
        <input type="text" id="Country" name="Country"><br>
        <label>First Name: </label><br>
        <input type="text" id ="firstName" name="firstName"><br>
        <label>Last Name: </label><br>
        <input type="text" id="lastName" name="lastName"><br>
        <label>Address: </label><br>
        <input type="text" id="address" name="address"><br>
        <label>City: </label><br>
        <input type="text" id="city" name="city"><br>
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
<form action="welcome.jsp" method="post">
    <label><input type="radio" name="deliveryMethod" value="Delivery" onchange="showOptions()"> Central Storefront </label><br>
    <label><input type="radio" name="deliveryMethod" value="Click&Collect" onchange="showOptions()"> Lidcombe Storefront </label><br>
    <label><input type="radio" name="deliveryMethod" value="Ship" onchange="showOptions()"> Bankstown Storefront </label><br>
    <input type="submit" value="Submit">
</form>
<%
        } else if (deliveryMethod.equals("Ship")){
%>
<h2>Select a collection point</h2>
<form action="welcome.jsp" method="post">
    <select id="Service" name="state" required>
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
