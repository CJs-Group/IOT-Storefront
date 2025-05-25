<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Users.Staff"%>
<%@page import="java.util.List"%>
<%@page import="Model.Items.ItemType"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
%>
<!DOCTYPE html>
<html>
    
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CJ MAXX</title>
    <link rel="stylesheet" type = "text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<body>
    <div class="topBar"></div>
        <img src="images/CJ_MAXX.png" class="logo">
        <div class="searchBarPos">
            <form action="/items.jsp" method="get">
                <input type="text" name="q" placeholder="Search.." class="searchBar">
            </form>
        </div>

        <div class="buttonContainer">
            <a href="login.jsp" class="registerContainer">
                <img src="images/login.png" class="registerIcon">
                <p class="registerText">Login</p>
            </a>
            
            <a href="register.jsp" class="registerContainer">
                <img src="images/user.png" class="registerIcon">
                <p class="registerText">Register</p>
            </a>

            <a href="pdbSystem/basket.jsp" class="cartContainer">
                <img src="images/cart.png" class="cartIcon">
                <p class="cartText">Cart</p>
            </a>
    </div>

    <div class="leftBar"></div>
    <div class="rightBar"></div>

    <img src="images/CJ_MAXX.png" class="logoLarge">

    <p class="featItemText">Featured Items</p>
    <div class="scrollContainer">
        <% for (ItemType it : dbm.getAllItemTypes()) { %> 
            <a href="/item.jsp?id=<%= it.getItemID() %>" class="itemCard">
                <img src="<%= it.getImagePath() %>" alt="<%= it.getName() %>" class="itemImage">
                <p><%= it.getName() %></p>
                <p>$<%= it.getPrice() %></p>
            </a>
        <% } %>    
    </div>
        
</body>

</html>