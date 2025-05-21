<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Items.ItemType"%>
<!DOCTYPE html>
<html>

<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
    ItemType[] items = dbm.getItemsByQuery(request.getParameter("q"));
%>

<body>
    <h1>STOREFRONT</h1>
    <h2>Items</h2>
    <% for (ItemType it : items) { %>
        <a href="/item.jsp?id=<%= it.getItemID() %>">
            <%= it.getItemID() %>
            <%= it.getName() %>
        </a>
        <br />
    <% } %>
</body>
</html>