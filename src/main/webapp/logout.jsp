<html>
<head>
    <title>Logout</title>
    <link rel="stylesheet" type="text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>
<body>

    <jsp:include page="includes/topbar.jsp" />

    <img src="images/CJ_MAXX.png" class="logoLarge">

    <% session.invalidate(); %>
    <p class="logoutText"> You have been logged out. Click <a href="index.jsp">here</a>
    to return to the main page. </p>
</body>
</html>