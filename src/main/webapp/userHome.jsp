<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>

<html>

    <body>
        <h3> Welcome </h3>
        <%
        DBConnector dbc = new DBConnector();
        DBManager dbm = new DBManager(dbc.openConnection());
        int userId = (int)session.getAttribute("userId");
        User user = dbm.getUserById(userId);
        // session.setAttribute("user", user);
        if (user != null) {
        %>
            <p align="right"> You are logged in as <%= user.getUsername() %> <%= user.getEmail() %> <br/>
            <a style="float:right" href="index.jsp">Storefront</a><br/>
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