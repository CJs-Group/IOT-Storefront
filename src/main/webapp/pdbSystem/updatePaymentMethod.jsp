<%@page import="Model.DB"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Order.PaymentInfo"%>
<html>
<head>
    <title>Update Payment Method</title>
</head>
<body>
<%
    int userId = (Integer) session.getAttribute("userId");
    Customer customer = (Customer) DB.getUserById(userId);
    PaymentInfo paymentInfo = customer.getPaymentInfo();
    String paymentErr = (String) session.getAttribute("paymentErr");
    String paymentSuccess = (String) session.getAttribute("paymentSuccess");
    
    if(paymentErr != null) {
%>
    <p style="color:red;"><%= paymentErr %></p>
<%
        session.removeAttribute("paymentErr");
    }
    if(paymentSuccess != null) {
%>
    <p style="color:green;"><%= paymentSuccess %></p>
<%
        session.removeAttribute("paymentSuccess");
    }
%>
<h1>Update Payment Method</h1>
<form action="../updatePaymentMethod" method="post">
    <label>Credit Card Number:</label><br>
    <input type="text" name="creditCardNumber" value="<%= paymentInfo.getCardNo() != null ? paymentInfo.getCardNo() : "" %>"><br>
    <label>Full Name:</label><br>
    <input type="text" name="fullName" value="<%= paymentInfo.getCardHolderName() != null ? paymentInfo.getCardHolderName() : "" %>"><br>
    <label>Expiry Date (MM/yy):</label><br>
    <input type="text" name="expiryDate" value="<%= paymentInfo.getExpiryDate() != null ? paymentInfo.getExpiryDate() : "" %>"><br>
    <label>CVV:</label><br>
    <input type="password" name="cvv" value=""><br><br>
    <input type="submit" value="Submit">
</form>
<br/>
Click <a href="../userHome.jsp">here</a> to proceed to the main page.<br/>
Click <a href="basket.jsp">here</a> to proceed to the basket.<br/>
</body>
</html>