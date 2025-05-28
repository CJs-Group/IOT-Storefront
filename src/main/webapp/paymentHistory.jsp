<%@page import="Model.Order.Payment"%>
<%@page import="Model.Order.PaymentInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Payment Management</title>
    <link rel="stylesheet" type="text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<body>
    <div class="topBar"></div>
    <img src="../images/CJ_MAXX.png" class="logo">
    <div class="searchBarPos">
        <input type="text" placeholder="Search.." class="searchBar">
    </div>

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

    <div class="mainText" style="margin-right: 17vh;">
        <h1>Payment Management</h1>

        <%
            String error = request.getParameter("error");
            String success = request.getParameter("success");
            if (error != null) {
        %>
            <p style="color: red;"><%= error %></p>
        <%
            }
            if (success != null) {
        %>
            <p style="color: green;"><%= success %></p>
        <%
            }
        %>

        <!-- search form -->
        <h2>Search Payment History</h2>
        <form method="get" action="paymentManagement">
            <table>
                <tr>
                    <td>Payment ID:</td>
                    <td><input type="number" name="paymentId" value="<%= request.getParameter("paymentId") != null ? request.getParameter("paymentId") : "" %>"></td>
                </tr>
                <tr>
                    <td>Start Date:</td>
                    <td><input type="date" name="startDate" value="<%= request.getParameter("startDate") != null ? request.getParameter("startDate") : "" %>"></td>
                </tr>
                <tr>
                    <td>End Date:</td>
                    <td><input type="date" name="endDate" value="<%= request.getParameter("endDate") != null ? request.getParameter("endDate") : "" %>"></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Search">
                        <a href="paymentManagement">Clear</a>
                    </td>
                </tr>
            </table>
        </form>

        <!-- payment history -->
        <h2>Payment History</h2>
        <%
            List<Payment> payments = (List<Payment>) request.getAttribute("payments");
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
            
            if (payments != null && !payments.isEmpty()) {
        %>
            <table border="1" style="width:100%; border-collapse: collapse;">
                <thead>
                    <tr>
                        <th>Payment ID</th>
                        <th>Order ID</th>
                        <th>Amount</th>
                        <th>Payment Date</th>
                        <th>Status</th>
                        <th>Card Details (Number / Name)</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Payment payment : payments) {
                            PaymentInfo paymentInfo = payment.getPaymentInfo();
                    %>
                        <tr>
                            <td><%= payment.getPaymentID() %></td>
                            <td><%= payment.getOrderID() %></td>
                            <td>$<%= String.format("%.2f", payment.getAmount() / 100.0) %></td>
                            <td><%= sdf.format(payment.getPaymentDate()) %></td>
                            <td><%= payment.getPaymentStatus().toString() %></td>
                            <td>
                                <%= paymentInfo != null ? "**** **** **** " + paymentInfo.getCardNo().substring(12) : "N/A" %><br/>
                                <%= paymentInfo != null ? paymentInfo.getCardHolderName() : "" %>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <p>No payment records found.</p>
        <%
            }
        %>

        <!-- saved payment methods management -->
        <h2>Saved Payment Methods</h2>
        <%
            List<PaymentInfo> savedPaymentMethods = (List<PaymentInfo>) request.getAttribute("savedPaymentMethods");
            
            if (savedPaymentMethods != null && !savedPaymentMethods.isEmpty()) {
        %>
            <table border="1" style="width:100%; border-collapse: collapse;">
                <thead>
                    <tr>
                        <th>Card Number</th>
                        <th>Cardholder Name</th>
                        <th>Expiry Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (PaymentInfo paymentMethod : savedPaymentMethods) {
                    %>
                        <tr>
                            <td>**** **** **** <%= paymentMethod.getCardNo().substring(12) %></td>
                            <td><%= paymentMethod.getCardHolderName() %></td>
                            <td><%= paymentMethod.getExpiryDate() %></td>
                            <td>
                                <a href="paymentManagement?action=delete&cardId=<%= paymentMethod.getPaymentId() %>" 
                                   onclick="return confirm('Are you sure you want to delete this payment method?')">Delete</a>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <p>No saved payment methods found.</p>
        <%
            }
        %>

        <br/>
        <p>
            <a href="updatePaymentMethod.jsp">Add New Payment Method</a><br/>
            <a href="index.jsp">Back to Home</a>
        </p>
    </div>
</body>
</html>