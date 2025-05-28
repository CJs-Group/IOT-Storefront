<%@page import="Model.Users.User"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="java.sql.Timestamp"%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CJ MAXX</title>
    <link rel="stylesheet" type = "text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<body>
    <%
        DBConnector dbc = new DBConnector();
        DBManager dbm = new DBManager(dbc.openConnection());
        int userId = (int)session.getAttribute("userId");
        User user = dbm.getUserById(userId);
        session.setAttribute("user", user);
        Timestamp lastLoginTime = (Timestamp)session.getAttribute("lastLoginTime");
    %>

    <jsp:include page="includes/topbar.jsp" />

    <img src="images/CJ_MAXX.png" class="logoLarge">
    
    <div>

        <p class="welcomeText">Welcome, <%= user.getUsername() %>, to the IOT Storefront!</p>
        
        <div class="userInfo">
            <p>Your account details:</p>
            <ul>
                <li>Email: <%= user.getEmail() %></li>
                <li>Phone: <%= user.getPhoneNumber() %></li>
                <% if (lastLoginTime != null) { %>
                    <li>Last login: <%= lastLoginTime %></li>
                <% } else { %>
                    <li>Welcome! This is your first login.</li>
                <% } %>
            </ul>

            <br><br><br>
        </div>
        <p class="returnText">Click <a href="userHome.jsp">here</a> to proceed to the main page.</p>
    </div>

</body>
</html>