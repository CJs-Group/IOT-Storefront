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
</head>
<body>
<h1>Basket</h1>

<%
  Connection conn = null;
  DBManager dbm = null;
  try {
    DBConnector dbConnector = new DBConnector();
    conn = dbConnector.openConnection();
    dbm = new DBManager(conn);

    int userId = (int)session.getAttribute("userId");
    Customer customer = (Customer)dbm.getUserById(userId);
    Basket basket = dbm.getBasketByUserId(userId, true);
    List<PaymentInfo> paymentInfos = dbm.getCardDetailsByUserId(userId);
    PaymentInfo paymentInfo = paymentInfos.get(0);
  
    if (paymentInfo == null || paymentInfo.getPaymentId() == -1) {
%>
<a style="float:left">You havent provided your Payment Method</a><br>
<a style="float:left">Please provide your Payment details</a><br>
<a style="float:left" href="updatePaymentMethod.jsp">Add a payment method</a><br>
<%
}
else {
%>
<label>Payment Details have been provided</label><br>
<a style="float:left" href="updatePaymentMethod.jsp">Change a payment method</a><br>
<%
  }
  if (basket == null || basket.getItems() == null || basket.getItems().isEmpty()){ 
%>
<label> Basket is empty </label><br>
<%
}
else {
%>
<label> Your Basket contains: </label><br>
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
</body>
</html>