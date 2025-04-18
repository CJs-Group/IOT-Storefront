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
import Model.Users.Customer;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");

            if (username == null || password == null || email == null) { // Check for null values
                response.sendRedirect("register.jsp?error=All fields are required.");
                return;
            }
            try {
                dbm.getUserByEmail(email);
                response.sendRedirect("register.jsp?error=Email already exists.");
                return;
            }
            catch (Exception _e) {}
            
            password = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

            Customer newCustomer = new Customer(0, username, password, email, "");
                dbm.createUser(newCustomer);
            
            // Add the new user id to session
            HttpSession session = request.getSession();
            session.setAttribute("userId", newCustomer.getUserID());
            response.sendRedirect("welcome.jsp");
        }
        catch (SQLException e) {
            response.sendRedirect("register.jsp?error=" +  e.getMessage());
        }
    }
}
