<%@page import="Model.Basket.Basket"%>
<%@page import="Model.Basket.BasketItem"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="Model.Order.PaymentInfo"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>

<html>
<head>
    <title>Basket</title>
    <link rel="stylesheet" type="text/css" href="../css/IoTBay.css">
    <link rel="icon" type="image/png" href="../images/CJ_MAXX.png">
</head>

<body>

<div class="topBar"></div>
    <img src="../images/CJ_MAXX.png" class="logo">
    <div class="searchBarPos"><input type="text" placeholder="Search.." class="searchBar"></div>

    <div class="buttonContainer">
        <a href="login.jsp" class="registerContainer">
            <img src="../images/login.png" class="registerIcon">
            <p class="registerText">Login</p>
        </a>
        
        <a href="register.jsp" class="registerContainer">
            <img src="../images/user.png" class="registerIcon">
            <p class="registerText">Register</p>
        </a>
    </div>

    <div class="leftBar"></div>
    <div class="rightBar"></div>

<p class="basketText">Basket</p>

<div class="bodyText">
<%
    String errorMessage = request.getParameter("error");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
    <p style="color:red; font-weight:bold;"><%= errorMessage.replace("+", " ") %></p>
<%
    }
  Connection conn = null;
  DBManager dbm = null;
  Basket basket = null;
  boolean isLoggedIn = false;
  try {
    DBConnector dbConnector = new DBConnector();
    conn = dbConnector.openConnection();
    dbm = new DBManager(conn);

    Object userIdObj = session.getAttribute("userId");

    if (userIdObj != null) {
      isLoggedIn = true;
      int userId = (int) userIdObj;
      Customer customer = (Customer)dbm.getUserById(userId);
      basket = dbm.getBasketByUserId(userId, true);
      List<PaymentInfo> paymentInfos = dbm.getCardDetailsByUserId(userId);
    
      if (paymentInfos.size() == 0) {
%>
<a style="float:left">You havent provided your Payment Method</a><br>
<a style="float:left">Please provide your Payment details</a><br><br>
<a style="float:left" href="updatePaymentMethod.jsp">Add a payment method</a><br><br>
<%
}
else {
%>
<label>Payment Details have been provided</label><br>
<a style="float:left" href="updatePaymentMethod.jsp">Change a payment method</a><br>
<%
      }
    } else {
      // User is not logged in - use session basket
      basket = (Basket) session.getAttribute("sessionBasket");
%>
<label>Shopping as Guest</label><br>
<a style="float:left">You can checkout as a guest or <a href="../login.jsp">login</a> for a better experience</a><br>
<%
    }
    
    if (basket == null || basket.getItems() == null || basket.getItems().isEmpty()){ 
%>
<label> Basket is empty </label><br><br>
<%
}
else {
%>
<h3> Your Basket contains: </h3>
<%
    // Updated loop to iterate over List<BasketItem>
    for (BasketItem basketItem : basket.getItems()) {
      ItemType itemType = basketItem.getItemType();
      int quantity = basketItem.getQuantity();
%>
<label>Item: <%= itemType.getName() %>, Quantity: <%= quantity %></label>
<form method="post" action="../updateBasket">
  <input type="hidden" name="itemId" value="<%= itemType.getItemID() %>">
  <button type="submit" name="action" value="remove">Remove</button>
  <button type="submit" name="action" value="+1">+1</button>
  <button type="submit" name="action" value="-1">-1</button>
</form>

<%
    }
%>
  Click <a href="checkout.jsp">here </a>to proceed for checkout. <br/>
<%
  }
%>
Click <a href="../userHome.jsp">here </a>to proceed to the main page. <br/>
<%
  }
  catch (Exception e) {
    out.println("<!-- An error occurred: " + e.getMessage() + " -->");
    e.printStackTrace(new java.io.PrintWriter(out));
  }
%>

</div>
</body>
</html>