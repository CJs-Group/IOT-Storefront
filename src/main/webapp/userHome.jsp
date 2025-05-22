<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CJ MAXX</title>
    <link rel="stylesheet" type = "text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<div class="topBar"></div>
        <img src="images/CJ_MAXX.png" class="logo">

    <div class="leftBar"></div>
    <div class="rightBar"></div>

    <img src="images/CJ_MAXX.png" class="logoLarge">

    <body>
        <%
        DBConnector dbc = new DBConnector();
        DBManager dbm = new DBManager(dbc.openConnection());
        int userId = (int)session.getAttribute("userId");
        User user = dbm.getUserById(userId);
        // session.setAttribute("user", user);
        if (user != null) {
        %>
            <p class="homeText"> You are logged in as <%= user.getUsername() %><br/>
            
            <img src="images/storefront.png" class="storefrontImage">
            <a href="index.jsp">Storefront</a><br/>
                <%
                    if(user instanceof Customer) {
                %>
                        <img src="images/cart.png" class="cartImage">
                        <a href="pdbSystem/basket.jsp">View basket</a><br/>
                <%
                    }
                %>
            <img src="images/user.png" class="logoutImage">
            <a href="logout.jsp">Logout</a><br>

        <%
        } else {
        %>
            <p align="center"> You are not logged in <br/>
            <a style="float:left" href="register.jsp">Register</a>
        <%
        }
        %>

    </body>
</html>