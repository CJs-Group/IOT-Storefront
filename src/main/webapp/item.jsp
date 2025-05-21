<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Items.ItemType"%>
<!DOCTYPE html>
<html>

<%
DBConnector dbc = new DBConnector();
DBManager dbm = new DBManager(dbc.openConnection());
ItemType item = dbm.getItemById(Integer.parseInt(request.getParameter("id")));
%>

<body>
    <h1>STOREFRONT</h1>
    <h2>Item <%= item.getItemID() %></h2>
    <h3>Name</h3>
    <p><%= item.getName() %></p>
    <h3>Price</h3>
    <p>$<%= item.getPrice() %></p>
    <h3>Description</h3>
    <p><%= item.getDescription() %></p>
</body>
</html>