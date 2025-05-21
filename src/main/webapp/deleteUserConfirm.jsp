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
    User userToDelete = null;
    String userIDStr = (String)session.getAttribute("selectedUserID");
    userToDelete = dbm.getUserById(Integer.parseInt(userIDStr));
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
                <h2>Confirm Deletion</h2>
                    <p>Are you sure you want to delete the user: <strong><%= userToDelete.getUsername() %></strong> (ID: <%= userToDelete.getUserID() %>)?</p>
                    <p>This action cannot be undone.</p>
                    <form action="${pageContext.request.contextPath}/userManip" method="post" style="display: inline;">
                        <input type="hidden" name="formAction" value="deleteUser">
                        <input type="hidden" name="selectedUserID" value="<%= userToDelete.getUserID() %>">
                        <input type="submit" value="Confirm Delete" class="button-danger">
                    </form>
                    <a href="userManagement.jsp" class="button">Cancel</a>
            </div>
        </div>
    </body>
</html>