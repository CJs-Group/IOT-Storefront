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

Item item = new Item("000", "undefined");
for (Item _item : itemList) {
    if (_item.getItemId().equals(request.getParameter("id"))) {
        item = _item;
    }
}
%>

<body>
    <h1>STOREFRONT</h1>
    <h2>Item <%= item.getItemId() %></h2>
    <h3>Item <%= item.getItemName() %></h3>
    <div>
    </div>
</body>
</html>