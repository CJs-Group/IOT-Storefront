<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.Item"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>

<%
List<Item> itemList = new ArrayList<Item>();
itemList.add(new Item("001","CPU"));
itemList.add(new Item("002","GPU"));
itemList.add(new Item("003","Motherboard"));
%>

<body>
    <h1>STOREFRONT</h1>
    <h2>Items</h2>
    <div>
        <% for (Item item : itemList) { %>
            <a href="/item.jsp?id=<%= item.getItemId() %>">
                <%= item.getItemId() %>
                <%= item.getItemName() %>
            </a>
            <br />
        <% } %>
    </div>
</body>
</html>