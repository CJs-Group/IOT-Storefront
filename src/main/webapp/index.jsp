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
        <%
        // Retrieve Alice assuming she's at index 1 of DB.users
        String aliceName = DB.users.get(2).getUsername();
        %>
        <p>Alices name: <%= aliceName %></p>
        <jsp:include page="/ConnServlet" flush="true" />
    </body>
</html>