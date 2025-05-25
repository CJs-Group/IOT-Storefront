<%@page import="Model.Users.Customer"%>
<%@page import="Model.Order.PaymentInfo"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<html>

<head>
    <title>Update Payment Method</title>
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

<div class="paymentMethodBodyText">
<br>
<%
    DBManager dbm = null;
    PaymentInfo paymentInfo = null;
    String paymentErr = (String) session.getAttribute("paymentErr");
    String paymentSuccess = (String) session.getAttribute("paymentSuccess");

    Integer userId = (Integer)session.getAttribute("userId");

    if (userId == null) {
        response.sendRedirect("../login.jsp?error=Please login to update payment method.");
        return;
    }

    try {
        DBConnector dbConnector = new DBConnector();
        Connection conn = dbConnector.openConnection();
        dbm = new DBManager(conn);

        List<PaymentInfo> userPaymentInfos = dbm.getCardDetailsByUserId(userId);
        if (userPaymentInfos != null && !userPaymentInfos.isEmpty()) {
            paymentInfo = userPaymentInfos.get(0);
        }
        else {
            paymentInfo = new PaymentInfo();
        }

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
    }
    catch (Exception e) {
        e.printStackTrace();
        paymentErr = "Error retrieving payment information: " + e.getMessage();
        if (paymentInfo == null) {
            paymentInfo = new PaymentInfo();
        }
    }
    finally {
        if (dbm != null) {
            try {
                dbm.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
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
</div>
</body>
</html>