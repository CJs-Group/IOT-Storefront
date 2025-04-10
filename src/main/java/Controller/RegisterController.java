package Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.hash.Hashing;

import javax.servlet.annotation.WebServlet;

import Model.DB;
import Model.Users.Customer;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (username == null || password == null || email == null) { // Check for null values
            response.sendRedirect("register.jsp?error=All fields are required.");
            return;
        }
        if (DB.users.stream().anyMatch(user -> user.getEmail().equals(email))) { // Check if email already exists
            response.sendRedirect("register.jsp?error=Email already exists.");
            return;
        }

        int newId = DB.users.size() + 1;
        
        password = Hashing.sha256()
            .hashString(password, StandardCharsets.UTF_8)
            .toString();

        Customer newCustomer = new Customer(newId, username, password, email, "");
        DB.users.add(newCustomer);

        // Add the new user id to session
        HttpSession session = request.getSession();
        session.setAttribute("userId", newId);
        response.sendRedirect("welcome.jsp");
    }
}
