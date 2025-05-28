<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Users.Staff"%>
<!DOCTYPE html>
<html>

<%
DBConnector dbc = new DBConnector();
DBManager dbm = new DBManager(dbc.openConnection());
Object userID = session.getAttribute("userId");
boolean isLoggedIn = userID != null;
boolean isCustomer = false;
boolean isStaff = false;
if (isLoggedIn) {
    try {
        User user = dbm.getUserById((Integer) userID);
        isCustomer = user instanceof Customer;
        isStaff = user instanceof Staff;
    }
    catch (Exception e) {
    }
}
ItemType item = null;
String itemIdParam = request.getParameter("id");
int availableStock = 0;
if (itemIdParam != null && !itemIdParam.isEmpty()) {
    try {
        item = dbm.getItemTypeById(Integer.parseInt(itemIdParam));
    }
    catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid item ID format.");
        return;
    }
}

if (item == null) {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found.");
    return;
}
else {
    availableStock = dbm.getItemQuantity(item.getItemID());
}
boolean showAddToCart = !isStaff && (!isLoggedIn || isCustomer);
%>

<head>
    <title><%=item.getName()%></title>
    <link rel="stylesheet" type="text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<body>
    <jsp:include page="includes/topbar.jsp" />

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
            <% if (availableStock > 0) { %>
                <p style="color: green; font-size: 16px;">In Stock: <%= availableStock %> available</p>
            <% } else { %>
                <p style="color: red; font-size: 24px; font-weight: bold;">OUT OF STOCK</p>
                <p style="color: red; font-size: 14px;">Currently unavailable</p>
            <% } %>
        </div>

        <% if (showAddToCart) { %>
            <div class="addCartContainer">
                <form action="${pageContext.request.contextPath}/updateBasket" method="post" style="display: inline;">
                    <input type="hidden" name="itemId" value="<%= item.getItemID() %>" />
                    <input type="hidden" name="action" value="+1" />
                    <button type="submit" class="addCartButton" <%= availableStock <= 0 ? "disabled" : "" %>>
                        <%= availableStock <= 0 ? "Out of Stock" : "Add to Cart" %>
                    </button>
                </form>
            </div>
        <% } else if (isStaff) { %>
            <div class="addCartContainer">
                <p style="color: #FFF; font-size: 14px;;">Staff members cannot add items to cart</p>
            </div>
        <% } %>
    </div>

    <div class="botBar">
        <p class="bottomLeftText">81-113, Broadway, Ultimo NSW 2007</p>
        <p class="bottomRightText">support@cjmaxx.com</p>
    </div>
</body>
</html>