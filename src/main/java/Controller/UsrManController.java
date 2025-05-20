package Controller;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.common.hash.Hashing;

import Model.Validator;
import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Order.PaymentInfo;
import Model.Users.Customer;
import Model.Users.User;

@WebServlet("/userManagement")
public class UsrManController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
        }
        catch (SQLException e) {
            response.sendRedirect("register.jsp?error=" +  e.getMessage());
        }
    }
}
