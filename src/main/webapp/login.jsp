<%-- 
    Document   : login
    Created on : 20/03/2024, 2:13:10 PM
    Author     : yih
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <div><span class="time" id="time"></span></div>
        <%
            String existErr = request.getParameter("existError");
            String emailErr = request.getParameter("emailError");
            String passErr = request.getParameter("passError");
        %>
        
        <h1>Sign In</h1>
        
        <% if(existErr != null || emailErr != null || passErr != null) { %>
            <div style="color: red; margin-bottom: 15px;">
                Error: <%= existErr != null ? existErr : emailErr != null ? emailErr : passErr %>
            </div>
        <% } %>
        <form action="login" method="post">
            <table id="form_table">
                <tr>
                    <td>Email:</td>
                    <td><input type="text" placeholder="<%=(emailErr != null ? emailErr: "Enter email")%>" name="email" required="true"></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" placeholder="<%=(passErr != null ? passErr: "Enter password")%>" name="password" required="true"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><a href="cancel.jsp" class="button"> Cancel</a>
                        <input class="button" type="submit" value="Sign In">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>