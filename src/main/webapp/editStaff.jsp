<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Users.Staff"%>
<%@page import="java.util.List"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
    String userIDStr = (String)session.getAttribute("selectedUserID");
    Staff staff = (Staff)dbm.getUserById(Integer.parseInt(userIDStr));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Staff</title>
        <link rel="stylesheet" type="text/css" href="css/userManagement.css">
    </head>
    <body>
        <div class="content-wrapper">
            <div class="container shown">
                <h2>Edit Staff</h2>
                <form action="${pageContext.request.contextPath}/userManip" method="post">
                    <input type="hidden" name="formAction" value="editStaff">
                    <input type="hidden" name="selectedUserID" value="<%= staff.getUserID() %>">
                    <label>Username:</label><br>
                    <input type="text" name="username" value="<%= staff.getUsername() %>" required><br>
                    <label>Email:</label><br>
                    <input type="email" name="email" value="<%= staff.getEmail() %>" required><br>
                    <label>New Password (leave blank to keep current):</label><br>
                    <input type="password" name="password"><br>
                    <label>Phone Number:</label><br>
                    <input type="text" name="phoneNumber" value="<%= staff.getPhoneNumber() != null ? staff.getPhoneNumber() : "" %>"><br>
                    <input type="submit" value="Update Staff">
                </form>
                <a href="userManagement.jsp">Cancel</a>
            </div>
        </div>
    </body>
</html>