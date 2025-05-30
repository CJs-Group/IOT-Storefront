<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>

<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
    String query = request.getParameter("q");
    List<ItemType> items = dbm.getItemTypesByQuery(query == null ? "" : query);
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