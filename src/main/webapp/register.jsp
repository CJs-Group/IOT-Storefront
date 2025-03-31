<%@page import="uts.isd.model.Basket"%>
<%@page import="uts.isd.model.Item"%>

<html>
<body>
<h2>Please Register</h2>
<%-- Method post observable in developer tools --%>
<%-- Note syntax for form action and method --%>
<%
    Basket basket = new Basket();
%>

<form action="welcome.jsp" method="post">
    <label>Email: </label><br>
    <input type="text" id="email" name="email"><br>
    <label>Name: </label><br>
    <input type="text" id="name" name="name"><br>
    <label>Password: </label><br>
    <input type="password" id ="password" name="password"><br>
    <label>Gender: </label><br>
    <input type="text" id="gender" name="gender"><br>
    <label>Favorite Colour: </label><br>
    <input type="color" id="favcol" name="favcol"><br>
    <label>Tos: </label><br>
    <input type="checkbox" name="tos"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>