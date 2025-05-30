<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.Staff"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CJ MAXX</title>
    <link rel="stylesheet" type = "text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<jsp:include page="includes/topbar.jsp" />

    <img src="images/CJ_MAXX.png" class="logoLarge">

    <body>
        <%
        Integer userIdObj = (Integer) session.getAttribute("userId");
        if (userIdObj != null) {
            DBConnector dbc = new DBConnector();
            DBManager dbm = new DBManager(dbc.openConnection());
            int userId = userIdObj;
            User user = dbm.getUserById(userId);
            
            if (user != null) {
        %>

            <p class="homeText"> You are logged in as <%= user.getUsername() %><br/>
            
            <br>

            <img src="images/storefront.png" class="storefrontImage">
            <a href="index.jsp">Storefront</a><br/>
                <%
                    if(user instanceof Customer) {
                %>
                        <img src="images/cart.png" class="cartImage">
                        <a href="basket.jsp">View basket</a><br/>
                        
                        <img src="images/user.png" class="logoutImage">
                        <a href="orders.jsp">View orders</a><br/>
                        
                        <img src="images/user.png" class="logoutImage">
                        <a href="paymentManagement">Payment management</a><br/>
                <%
                    } else {
                        Staff staff = (Staff) user;
                %>
                        <img src="images/list.png" class="cartImage">
                        <a href="stock.jsp">View Stock</a><br/>
                        
                        <img src="images/list.png" class="cartImage">
                        <a href="itemManagement.jsp">Item Management</a><br/>
                        
                        <% if (staff.isAdmin()) { %>
                            <img src="images/user.png" class="logoutImage">
                            <a href="userManagement.jsp">User Management</a><br/>
                        <% } %>
                <%
                    }
                %>
            <img src="images/user.png" class="logoutImage">
            <a href="logout.jsp">Logout</a><br>

        <%
            } else {
        %>
            <p align="center"> User not found <br/>
            <a href="index.jsp">Storefront</a><br/>
        <%
            }
        } else {
        %>
            <p align="center"> You are not logged in <br/>
            <a href="index.jsp">Storefront</a><br/>
            <a href="register.jsp">Register</a>
        <%
        }
        %>

    </body>
</html>