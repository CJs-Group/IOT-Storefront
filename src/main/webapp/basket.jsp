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
<%@page import="java.util.ArrayList"%>

<html>
<head>
    <title>Basket</title>
    <link rel="stylesheet" type="text/css" href="../css/IoTBay.css">
    <link rel="icon" type="image/png" href="../images/CJ_MAXX.png">
</head>

<body>

<style>
  body {
    overflow: auto;
  }
</style>

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



<div class="bodyText">
<p class="basketText">Basket</p>
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
  boolean allowedToCheckout = true;
  try {
    DBConnector dbConnector = new DBConnector();
    conn = dbConnector.openConnection();
    dbm = new DBManager(conn);

    Object userIdObj = session.getAttribute("userId");
    List<PaymentInfo> paymentInfos = null;
    if (userIdObj != null) {
      isLoggedIn = true;
      int userId = (int) userIdObj;
      Customer customer = (Customer)dbm.getUserById(userId);
      basket = dbm.getBasketByUserId(userId, true);
      paymentInfos = dbm.getCardDetailsByUserId(userId);
    }
    else {
      // User is not logged in - use session basket and payment info
      basket = (Basket) session.getAttribute("sessionBasket");
      if (basket == null) {
        basket = new Basket(-1);
        session.setAttribute("sessionBasket", basket);
      }
      paymentInfos = (List<PaymentInfo>) session.getAttribute("paymentInfos");
      if (paymentInfos == null) {
        paymentInfos = new ArrayList<>();
        session.setAttribute("paymentInfos", paymentInfos);
      }
    }
    
    if (isLoggedIn) {
      if (paymentInfos.size() == 0) {
%>
<a>No payment details present.</a><br>
<a>Please provide payment details <a href="updatePaymentMethod.jsp">here</a>.<br>
<%
      } else {
%>
<label>Payment details have been provided.</label><br>
<a>Change payment method <a href="updatePaymentMethod.jsp">here</a>.<br>
<%
      }
    } else {
%>
<label>Currently shopping as a Guest.</label><br>
<a>You can either checkout as a guest, or <a href="login.jsp">login</a> for a better experience.</a><br>
<br/>
<%
  if (paymentInfos.size() == 0) {
%>
<a>No payment details present.</a><br>
<a>Please provide payment details <a href="updatePaymentMethod.jsp">here</a>.<br>
<%
  } else {
%>
<label>Payment details have been provided.</label><br>
<a>Change payment method <a href="updatePaymentMethod.jsp">here</a>.<br>
<%
  }
    }
    
    if (basket == null || basket.getItems() == null || basket.getItems().isEmpty()){ 
%>
<br/>
<label>Your basket is empty.</label><br>
<a>Click <a href="index.jsp">here</a> to go to the storefront.</a><br>
<br/>
<%
}
else {
%>
</br><label>Your basket currently contains: </label><br>
<%
    for (BasketItem basketItem : basket.getItems()) {
      ItemType itemType = basketItem.getItemType();
      int quantity = basketItem.getQuantity();
      int availableStock = dbm.getItemQuantity(itemType.getItemID());
      boolean canAddMore = availableStock > quantity;
      if (availableStock < quantity) {
        allowedToCheckout = false;
      }
%>
<label>Item: <%= itemType.getName() %>, Quantity: <%= quantity %></label>
<form method="post" action="updateBasket">
  <input type="hidden" name="itemId" value="<%= itemType.getItemID() %>">
  <button type="submit" name="action" value="remove">Remove</button>
  <button type="submit" name="action" value="+1" <%= !canAddMore ? "disabled" : "" %>>+1</button>
  <button type="submit" name="action" value="-1">-1</button>
</form>
<a>Click <a href="index.jsp">here</a> to go to the storefront.</a><br>
<br/>
<%
    }
%>
<%
  // Only show checkout link if user has payment details and stock is available
  if (paymentInfos.size() > 0 && allowedToCheckout) {
%>
  Click <a href="checkout.jsp">here </a>to proceed for checkout.<br/>
<%
  } else if (paymentInfos.size() == 0) {
%>
  <label>Please provide payment details before checkout.</label><br>
  <br/>
<%
  } else if (!allowedToCheckout) {
%>
  <label>Some items in your basket are out of stock. Please adjust quantities before checkout.</label><br/>
<%
  }
}
%>
Click <a href="userHome.jsp">here</a> to go to your home page.<br>
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