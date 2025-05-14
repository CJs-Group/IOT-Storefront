<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DB"%>
<%@page import="Model.Items.ItemType"%>
<!DOCTYPE html>
<html>

<%
ItemType item = DB.getItemById(Integer.parseInt(request.getParameter("id")));
if (item == null) {
    return;
}
%>

<head>
    <title><%=item.getName()%></title>
    <link rel="stylesheet" type = "text/css" href="IoTBay.css">
</head>

<body>
    <div class="topBar"></div>
    <img src="images/CJ_MAXX.png" class="logo">
    <div class="searchBarPos"><input type="text" placeholder="Search.." class="searchBar"></div>

    <div class="buttonContainer">
        <a href="register.jsp" class="registerContainer">
            <img src="images/user.png" class="registerIcon">
            <p class="registerText">Register</p>
        </a>

        <a href="placeholder" class="cartContainer">
            <img src="images/cart.png" class="cartIcon">
            <p class="cartText">Cart</p>
        </a>
    </div>

    <div class="leftBar"></div>
    <div class="rightBar"></div>

    <div class="mainText">
        <div class="prodImageContainer"><img src="<%= item.getImagePath() %>" class="prodImage"></div>
        <div class="descText">
            <h1><%= item.getName() %></h1>
            <div class="priceTextContainer"><p class="priceText">Price: $<%= item.getPrice() %></p></div>
            <p>ID: <%= item.getItemID() %></p>
            <p><%= item.getDescription() %></p>
        </div>

        <div class="addCartContainer"><p class="addCartText">Add to Cart</p></div>
    </div>

    <div class="botBar">
        <p class="bottomLeftText">81-113, Broadway, Ultimo NSW 2007</p>
        <p class="bottomRightText">support@cjmaxx.com</p>
        </div>
</body>
</html>