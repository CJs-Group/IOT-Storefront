<%@page import="Model.Users.Basket"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="Model.Order.PaymentInfo"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.DB"%>
<%@page import="java.util.Map"%>

<html>
<head>
  <title>Basket</title>
</head>
<body>
<h1>Basket</h1>

<%
  int userId = (int)session.getAttribute("userId");
  Customer customer = (Customer)DB.getUserById(userId);
  Basket basket = customer.getBasket();
  PaymentInfo paymentInfo = customer.getPaymentInfo();
  
  if (paymentInfo.getPaymentId() == -1) {
%>
<a style="float:left">You haven't provided your Payment Method</a><br>
<a style="float:left">Please provide your Payment details</a><br>
<a style="float:left" href="updatePaymentMethod.jsp">Add a payment method</a><br>
<%
} else {
%>
<label>Payment Details have been provided</label><br>
<a style="float:left" href="updatePaymentMethod.jsp">Change a payment method</a><br>
<%
  }
  if (basket == null || basket.getBasketSize() == 0){
%>
<label> Basket is empty </label><br>
<%
} else {
%>
<label> Your Basket contains: </label><br>
<%
    for (Map.Entry<ItemType, Integer> entry : basket.getItems().entrySet()) {
%>
<label>Item: <%= entry.getKey().getName() %>, Quantity: <%= entry.getValue() %></label>
<form method="post" action="../updateBasket">
  <input type="hidden" name="itemId" value="<%= entry.getKey().getItemID() %>">
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
</body>
</html>