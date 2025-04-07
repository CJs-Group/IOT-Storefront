<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DB"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <h1>STOREFRONT</h1>
        <div>
            <a class="button" href="register.jsp">Register</a>
        </div>
        <div>
            <a class="button" href="login.jsp">Login</a>
        </div>
        <jsp:include page="/ConnServlet" flush="true" />
    </body>
</html>