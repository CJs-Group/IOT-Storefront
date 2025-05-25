<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Items.ItemType"%>
<!DOCTYPE html>
<html>

<%
DBConnector dbc = new DBConnector();
DBManager dbm = new DBManager(dbc.openConnection());
ItemType item = null;
String itemIdParam = request.getParameter("id");
if (itemIdParam != null && !itemIdParam.isEmpty()) {
    try {
        item = dbm.getItemById(Integer.parseInt(itemIdParam));
    } catch (NumberFormatException e) {
        // Handle invalid ID format, e.g., redirect or show error
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid item ID format.");
        return;
    }
}

if (item == null) {
    // Handle item not found, e.g., redirect or show error
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found.");
    return;
}
%>

<head>
    <title><%=item.getName()%></title>
    <link rel="stylesheet" type="text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<body>
    <div class="topBar"></div>
    <img src="images/CJ_MAXX.png" class="logo">
    <div class="searchBarPos"><input type="text" placeholder="Search.." class="searchBar"></div>

    <div class="buttonContainer">
        <a href="login.jsp" class="registerContainer">
            <img src="images/login.png" class="registerIcon">
            <p class="registerText">Login</p>
        </a>
        
        <a href="register.jsp" class="registerContainer">
            <img src="images/user.png" class="registerIcon">
            <p class="registerText">Register</p>
        </a>

        <a href="/pdbSystem/basket.jsp" class="cartContainer">
            <img src="images/cart.png" class="cartIcon">
            <p class="cartText">Cart</p>
        </a>
    </div>

    <div class="leftBar"></div>
    <div class="rightBar"></div>

    <div class="mainText">
        <div class="prodImageContainer">
            <%-- Assuming ItemType has a getImagePath() method --%>
            <% if (item.getImagePath() != null && !item.getImagePath().isEmpty()) { %>
                <img src="<%= item.getImagePath() %>" class="prodImage" alt="<%= item.getName() %>">
            <% } else { %>
                <img src="images/default_product.png" class="prodImage" alt="Default product image">
            <% } %>
        </div>
        <div class="descText">
            <h1><%= item.getName() %></h1>
            <div class="priceTextContainer">
                <p class="priceText">Price: $<%= String.format("%.2f", item.getPrice() / 100.0 * 100) %></p>
            </div>
            <p>ID: <%= item.getItemID() %></p>
            <p><%= item.getDescription() %></p>
        </div>

        <div class="addCartContainer">
            <form action="${pageContext.request.contextPath}/updateBasket" method="post" style="display: inline;">
                <input type="hidden" name="itemId" value="<%= item.getItemID() %>" />
                <input type="hidden" name="action" value="+1" />
                <button type="submit" class="addCartButton">Add to Cart</button>
            </form>
        </div>
    </div>

    <div class="botBar">
        <p class="bottomLeftText">81-113, Broadway, Ultimo NSW 2007</p>
        <p class="bottomRightText">support@cjmaxx.com</p>
    </div>
</body>
</html>