<%@page import="uts.isd.model.Basket"%>
<%@page import="uts.isd.model.CreditCard"%>
<%@page import="uts.isd.model.Item"%>
<%@page import="java.util.Map"%>

<html>
<h1> Basket </h1>
<%
  String itemId = request.getParameter("itemId");
  String updateBasket = request.getParameter("updateBasket");
%>
<body>
<%
  if (updateBasket != null && updateBasket.equals("remove")) {
    Basket basket = (Basket) session.getAttribute("basket");
    if (basket != null) {
      basket.removeItem(itemId);
    }
  }
  CreditCard creditCard = (CreditCard) session.getAttribute("creditCard");
  if (creditCard == null){
%>
<a style="float:left"> You haven't provided your Payment Method</a><br>
<a style="float:left"> Please provide your Credit Card details</a><br>
<a style="float:left" href="updatePaymentMethod.jsp">Add a payment method</a><br>
<%
} else {
%>
<label> Credit Card Details have been provided </label><br>
<a style="float:left" href="updatePaymentMethod.jsp">Change a payment method</a><br>
<%
  }
  Basket basket = (Basket) session.getAttribute("basket");
  if (basket == null || basket.getBasketSize() == 0){
%>
<label> Basket is empty </label><br>
<%
} else {
%>
<label> Your Basket contains: </label><br>
<%
  for (Map.Entry<Item, Integer> entry : basket.getItems().entrySet()) {
%>
<label> Item: <%= entry.getKey().getItemName() %>, Quantity: <%= entry.getValue() %> </label>
<form method="post" action="basket.jsp">
  <input type="hidden" name="updateBasket" value="remove">
  <input type="hidden" name="itemId" value="<%= entry.getKey().getItemId() %>">
  <button type="submit" name="action" value="remove">Remove</button>
</form>

<%
    }
  }
%>
Click <a href="checkout.jsp">here </a>to proceed for checkout. <br/>
Click <a href="../userHome.jsp">here </a>to proceed to the main page. <br/>
</body>
</html>