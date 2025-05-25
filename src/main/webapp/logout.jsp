<html>
<head>
    <title>Logout</title>
    <link rel="stylesheet" type="text/css" href="css/IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>
<body>

    <div class="topBar"></div>
    <img src="images/CJ_MAXX.png" class="logo">
    <div class="searchBarPos"><input type="text" placeholder="Search.." class="searchBar"></div>

    <div class="buttonContainer">
        <a href="login.jsp" class="registerContainer">
            <img src="images/login.png" class="registerIcon">
            <p class="registerText">Login</p>
        </a>
        
        <a href="register.jsp" class="registerContainer">
            <img src="images/user.png" class="registerIcon">
            <p class="registerText">Register</p>
        </a>

        <a href="pdbSystem/basket.jsp" class="cartContainer">
            <img src="images/cart.png" class="cartIcon">
            <p class="cartText">Cart</p>
        </a>
    </div>

    <div class="leftBar"></div>
    <div class="rightBar"></div>

    <img src="images/CJ_MAXX.png" class="logoLarge">

    <% session.invalidate(); %>
    <p class="logoutText"> You have been logged out. Click <a href="index.jsp">here</a>
    to return to the main page. </p>
</body>
</html>