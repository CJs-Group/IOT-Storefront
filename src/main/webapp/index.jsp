<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Users.Staff"%>
<%@page import="java.util.List"%>
<%@page import="Model.Items.ItemType"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
%>
<!DOCTYPE html>
<html>
    
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CJ MAXX</title>
    <link rel="stylesheet" type = "text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<body>
    <jsp:include page="includes/topbar.jsp" />

    <img src="images/CJ_MAXX.png" class="logoLarge">

    <p class="featItemText">Featured Items</p>
    <div class="scrollContainer">
        <%
        String query = request.getParameter("q");
        List<ItemType> items = dbm.getItemTypesByQuery(query == null ? "" : query);
        
        if (items.isEmpty()) { %>
            <div style="background-color: #333; color: white; padding: 40px; text-align: center; margin: 20px auto; border-radius: 8px; max-width: 400px;">
                <p style="font-size: 18px; margin-bottom: 20px;">No items found in search</p>
                <form action="index.jsp" method="get" style="display: inline;">
                    <button type="submit" style="background-color: #666; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; font-size: 16px;">
                        Reset Search
                    </button>
                </form>
            </div>
        <% } else {
            for (ItemType it : items) { %> 
                <a href="/item.jsp?id=<%= it.getItemID() %>" class="itemCard">
                    <img src="<%= it.getImagePath() %>" alt="<%= it.getName() %>" class="itemImage">
                    <p><%= it.getName() %></p>
                    <p>$<%= it.getPrice() %></p>
                </a>
            <% } 
        } %> 
    </div>
        
</body>

</html>