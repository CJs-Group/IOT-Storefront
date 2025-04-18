package Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.hash.Hashing;

import javax.servlet.annotation.WebServlet;

import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Users.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
            HttpSession session = request.getSession();
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            if (email == null || email.isEmpty()) {
                response.sendRedirect("login.jsp?emailError=Email cannot be empty");
                return;
            }
            
            if (password == null || password.isEmpty()) {
                response.sendRedirect("login.jsp?passError=Password cannot be empty");
                return;
            }
            
            String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

            
            User user = dbm.getUserByEmail(email);
            session.setAttribute("userId", user.getUserID());
            response.sendRedirect("welcome.jsp");

            if (user.getPassword() == hashedPassword) {
                response.sendRedirect("register.jsp?existError=Incorrect username or password.");
            }
        }
        catch (SQLException e) {
            response.sendRedirect("register.jsp?existError=Incorrect username or password.");
        }
        
    }
}
