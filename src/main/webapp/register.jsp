<!DOCTYPE html>
<html>
<body>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CJ MAXX</title>
    <link rel="stylesheet" type = "text/css" href="IoTBay.css">
    <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
</head>

<body>
    <div class="topBar"></div>
        <img src="images/CJ_MAXX.png" class="logo">
    </div>

    <div class="leftBar"></div>
    <div class="rightBar"></div>

    <img src="images/CJ_MAXX.png" class="logoLarge">

    <div class="botBar">
        <p class="bottomLeftText">81-113, Broadway, Ultimo NSW 2007</p>
        <p class="bottomRightText">support@cjmaxx.com</p>
    </div>

<h2>Please Register</h2>
<%-- Method post observable in developer tools --%>
<%-- Note syntax for form action and method --%>
<% 
    String errorMsg = request.getParameter("error");
%>

<% if(errorMsg != null && !errorMsg.isEmpty()) { %>
    <div style="color: red; margin-bottom: 15px;">
        Error: <%= errorMsg %>
    </div>
<% } %>
<form action="register" method="post">
    <label>Email: </label><br>
    <input type="text" id="email" name="email"><br>
    <label>Username: </label><br>
    <input type="text" id="username" name="username"><br>
    <label>Password: </label><br>
    <input type="password" id ="password" name="password"><br>
    <label>Tos: </label><br>
    <input type="checkbox" name="tos"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>