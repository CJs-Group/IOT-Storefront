<%@page import="Model.DB"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.DB"%>
<%@page import="Model.Items.ItemType"%>

<html>

    <body>
        <h3> Welcome </h3>
        <%
<<<<<<< Updated upstream
        User user = (User) session.getAttribute("user");
=======
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
            User user = (User) session.getAttribute("user");
>>>>>>> Stashed changes
            if (user != null) {
        %>
                <p align="right"> You are logged in as <%= user.getUsername() %> <%= user.getEmail() %> <br/>
                    <%
                        if(user instanceof Customer) {
                    %>
                            <a style="float:right" href="pdbSystem/basket.jsp">View basket</a><br/>
                    <%
                        }
                    %>
<<<<<<< Updated upstream
                    <a style="float:right" href="pdbSystem/orders.jsp">View orders</a><br/>
                <a style="float:right" href="logout.jsp">Logout</a><br>
        <%
            } else {
        %>
                <p align="center"> You are not logged in <br/>
                <a style="float:left" href="register.jsp">Register</a><br/>
                <a style="float:left" href="login.jsp">Login</a>
        <%
            }
=======
                <a style="float:right" href="pdbSystem/orders.jsp">View orders</a><br/>
                <a style="float:right" href="logout.jsp">Logout</a><br>

            <%
            } else {
            %>
                <p align="center"> You are not logged in <br/>
                <a style="float:left" href="register.jsp">Register</a>
            <%
            }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
>>>>>>> Stashed changes
        %>
        <% for (ItemType it : DB.items) { %>
            <a href="/item.jsp?id=<%= it.getItemID() %>">
                <%= it.getItemID() %>
                <%= it.getName() %>
            </a>
            <br />
        <% } %>
    </body>
</html>