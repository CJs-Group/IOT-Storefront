<%@page import="uts.isd.model.CreditCard"%>

<html>
<%
  String creditCardNumber = request.getParameter("creditCardNumber");
  String fullName = request.getParameter("fullName");
  String expiryDate = request.getParameter("expiryDate");
  String cvv = request.getParameter("cvv");
  String updatePaymentMethod = request.getParameter("updatePaymentMethod");
%>

<body>
<%
  if (creditCardNumber != null && updatePaymentMethod.equals("updatePaymentMethod")) {
    CreditCard creditCard = new CreditCard(creditCardNumber, fullName, expiryDate, cvv);
    if (creditCard.validate()) {
      session.setAttribute("creditCard", creditCard);
%>
    Successfully updated the account
<%
    } else {
      session.removeAttribute("creditCard");
%>
    Invalid payment details. Please re-enter details.
<%
    }
  }
%>

<h1>Update Credit Card</h1>
<form action="updatePaymentMethod.jsp" method="post">
  <label>Credit Card Number: </label><br>
  <input type="text" name="creditCardNumber"><br>
  <label>Full Name: </label><br>
  <input type="text" name="fullName"><br>
  <label>Expiry Date (dd/mm/yy): </label><br>
  <input type="text" name="expiryDate"><br>
  <label>CVV/CVC: </label><br>
  <input type="password" name="cvv"><br>
  <input type="hidden" name="updatePaymentMethod" value="updatePaymentMethod"><br>
  <input type="submit" value="Submit">
</form>

Click <a href="../userHome.jsp">here </a>to proceed to the main page. <br/>
Click <a href="basket.jsp">here </a>to proceed to the basket. <br/>
</body>
</html>