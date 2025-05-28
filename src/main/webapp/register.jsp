<!DOCTYPE html>
<html>
<body>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CJ MAXX</title>
        <link rel="stylesheet" type = "text/css" href="css/IoTBay.css">
        <link rel="icon" type="image/png" href="images/CJ_MAXX.png">
    </head>

    <body>
        <jsp:include page="includes/topbar.jsp" />

        <img src="images/CJ_MAXX.png" class="logoLarge">

        <div class="registerPageText">
            <h2>Please Register</h2>
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
                <label>Account Type: </lavel><br>
                <%@ page import="Model.Users.AccountType" %>
                <select id="accountType" name="accountType">
                    <% for (AccountType type : AccountType.values()) { %>
                        <option value="<%= type.name() %>"><%= type.name() %></option>
                    <% } %>
                </select><br>

                <%-- <select id="accountType" name="accountType">
                    <option value="Individual">Individual</option>
                    <option value="Business">Business</option>
                    <option value="Enterprise">Enterprise</option>
                </select><br> --%>
                <label>Do You Agree to the Terms of Service?: </label>
                <input type="checkbox" name="tos"><br>
                <input type="submit" value="Submit">
            </form>
        </div>

        <div class="botBar">
            <p class="bottomLeftText">81-113, Broadway, Ultimo NSW 2007</p>
            <p class="bottomRightText">support@cjmaxx.com</p>
        </div>
    </body>
</html>