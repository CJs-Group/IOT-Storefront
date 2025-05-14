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

<head>
    <title><%=item.getName()%></title>
    <link rel="stylesheet" type = "text/css" href="IoTBay.css">
</head>

<body>
    <div class="topBar"></div>
    <img src="images/CJ_MAXX.png" class="logo">
    <div class="searchBarPos"><input type="text" placeholder="Search.." class="searchBar"></div>

    <div class="leftBar"></div>
    <div class="rightBar"></div>

    <div class="mainText">
        <div class="prodImageContainer"><img src="<%= item.getImagePath() %>" class="prodImage"></div>
        <div class="descText">
            <h1><%= item.getName() %></h1>
            <h2>ID: <%= item.getItemID() %></h2>
            <p>Price: $<%= item.getPrice() %></p>
            <p><%= item.getDescription() %></p>
        </div>
    </div>

    <div class="botBar"></div>
</body>
</html>