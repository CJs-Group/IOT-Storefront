package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import Model.DB;
import Model.Users.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("existErr");
        session.removeAttribute("emailErr");
        session.removeAttribute("passErr");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || email.isEmpty()) {
            session.setAttribute("emailErr", "Email cannot be empty");
            response.sendRedirect("login.jsp");
            return;
        }
        
        if (password == null || password.isEmpty()) {
            session.setAttribute("passErr", "Password cannot be empty");
            response.sendRedirect("login.jsp");
            return;
        }
        
        boolean userFound = false;
        for (User user : DB.users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                userFound = true;
                session.setAttribute("userId", user.getUserID());
                response.sendRedirect("welcome.jsp");
                return;
            }
        }
        
        if (!userFound) {
            session.setAttribute("existErr", "Incorrect email or password");
            response.sendRedirect("login.jsp");
        }
    }
}
