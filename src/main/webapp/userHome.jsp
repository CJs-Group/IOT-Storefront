<%@page import="Model.DB"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>

<html>

    <body>
        <h3> Welcome </h3>
        <%
        int userId = (int)session.getAttribute("userId");
        User user = DB.getUserById(userId);
        session.setAttribute("user", user);
        if (user != null){
        %>
            <p align="right"> You are logged in as <%= user.getUsername() %> <%= user.getEmail() %> <br/>
                <%
                    if(user instanceof Customer) {
                %>
                        <a style="float:right" href="pdbSystem/basket.jsp">View basket</a><br/>
                <%
                    }
                %>
            <a style="float:right" href="logout.jsp">Logout</a><br>

        <%
        } else {
        %>
            <p align="center"> You are not logged in <br/>
            <a style="float:left" href="register.jsp">Register</a>
        <%
        }
        %>

    </body>
</html>