<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DB"%>
<%@page import="Model.Items.ItemType"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <h1>STOREFRONT</h1>
        <h2>Actions</h2>
        <div>
            <a class="button" href="register.jsp">Register</a>
        </div>
        <div>
            <a class="button" href="login.jsp">Login</a>
            <a class="button" href="userManagement.jsp">Not Login</a>
        </div>
        <h2>Items</h2>
        <% for (ItemType it : DB.items) { %>
            <a href="/item.jsp?id=<%= it.getItemID() %>">
                <%= it.getItemID() %>
                <%= it.getName() %>
            </a>
            <br />
        <% } %>
    </body>
</html>