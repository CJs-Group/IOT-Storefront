<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.Users.Staff"%>
<%@page import="java.util.List"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
    Object userID = session.getAttribute("userId");
    boolean isLoggedIn = userID != null;
    boolean isCustomer = false;
    if (isLoggedIn) {
        try {
            User user = dbm.getUserById((Integer) userID);
            isCustomer = user instanceof Customer;
        } catch (Exception e) {
            // Handle error silently, default to false
        }
    }
%>
<div class="topBar"></div>
<a href="${pageContext.request.contextPath}/index.jsp" class="logoLink">
    <img src="${pageContext.request.contextPath}/images/CJ_MAXX.png" class="logo">
</a>
<div class="searchBarPos">
    <form action="/index.jsp" method="get">
        <input type="text" name="q" placeholder="Search.." class="searchBar">
    </form>
</div>

<div class="buttonContainer">
    <% if (isLoggedIn) { %>
        <!-- Show UserHome when logged in -->
        <a href="${pageContext.request.contextPath}/userHome.jsp" class="registerContainer">
            <img src="${pageContext.request.contextPath}/images/user.png" class="registerIcon">
            <p class="registerText">UserHome</p>
        </a>
    <% } else { %>
        <!-- Show Login and Register when not logged in -->
        <a href="${pageContext.request.contextPath}/login.jsp" class="registerContainer">
            <img src="${pageContext.request.contextPath}/images/login.png" class="registerIcon">
            <p class="registerText">Login</p>
        </a>
        
        <a href="${pageContext.request.contextPath}/register.jsp" class="registerContainer">
            <img src="${pageContext.request.contextPath}/images/user.png" class="registerIcon">
            <p class="registerText">Register</p>
        </a>
    <% } %>

    <% if (!isLoggedIn || isCustomer) { %>
        <a href="${pageContext.request.contextPath}/basket.jsp" class="cartContainer">
            <img src="${pageContext.request.contextPath}/images/cart.png" class="cartIcon">
            <p class="cartText">Cart</p>
        </a>
    <% } %>
</div>

<div class="leftBar"></div>
<div class="rightBar"></div>