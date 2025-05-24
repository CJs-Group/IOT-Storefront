<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Users.Staff"%>
<%@page import="Model.Users.AccountType"%>
<%@page import="java.util.List"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Customer</title>
        <link rel="stylesheet" type="text/css" href="css/userManagement.css">
    </head>
    <body>
        <div class="content-wrapper">
            <div class="container shown">
                <h2>Add Customer</h2>
                    <% 
                        String formError = (String) session.getAttribute("formError");
                        if (formError != null) {
                    %>
                        <p style="color: red;"><%= formError %></p>
                    <%
                    }
                    %>
                <form action="${pageContext.request.contextPath}/userManip" method="post">
                    <input type="hidden" name="formAction" value="addCustomer">
                    <label>Username:</label><br>
                    <input type="text" name="username" required><br>
                    <label>Password:</label><br>
                    <input type="password" name="password" required><br>
                    <label>Email:</label><br>
                    <input type="email" name="email" required><br>
                    <label>Phone Number:</label><br>
                    <input type="text" name="phone"><br>
                    <label>Address:</label><br>
                    <input type="text" name="address"><br><br>
                    <label>Account Type:</label><br>
                    <select name="accountType" required>
                        <% for (AccountType type : AccountType.values()) { %>
                            <option value="<%= type.name() %>"><%= type.toString() %></option>
                        <% } %>
                    </select><br><br>
                    <input type="submit" value="Add Customer">
                </form>
                <a href="userManagement.jsp">Cancel</a>
            </div>
        </div>
    </body>
</html>