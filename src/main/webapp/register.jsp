<html>
<body>
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