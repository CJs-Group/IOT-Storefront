<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DB"%>
<%@page import="Model.Items.ItemType"%>
<!DOCTYPE html>
<html>

<%
ItemType item = DB.getItemById(Integer.parseInt(request.getParameter("id")));
if (item == null) {
    return;
}
%>

<body>
    <h1>STOREFRONT</h1>
    <h2>Item <%= item.getItemID() %></h2>
    <h3>Item <%= item.getName() %></h3>
    <div>
        <p>Price: <%= item.getPrice() %></p>
        <p>Description: <%= item.getDescription() %></p>
        <form method="post" action="../updateBasket">
            <input type="hidden" name="itemId" value="<%= item.getItemID() %>">
            <input type="hidden" name="action" value="<%= "add" %>">
            <button type="submit">Add to Basket</button>
        </form>
    </div>
</body>
</html>