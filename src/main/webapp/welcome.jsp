<%@page import="Model.DB"%>
<%@page import="Model.Users.User"%>

<html>
<head>
    <title>Welcome to IOT Storefront</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%
        int userId = (int)session.getAttribute("userId");
        User user = DB.getUserById(userId);
        session.setAttribute("user", user);
    %>
    
    <div class="welcome-container">
        <h1>Welcome to IOT Storefront</h1>
        
        <div class="user-info">
            <h2>Welcome, <%= user.getUsername() %>!</h2>
            <p>Your account details:</p>
            <ul>
                <li>Email: <%= user.getEmail() %></li>
                <li>Phone: <%= user.getPhoneNumber() %></li>
            </ul>
            
            <p>Click <a href="userHome.jsp">here</a> to proceed to the main page.</p>
        </div>
    </div>
</body>
</html>