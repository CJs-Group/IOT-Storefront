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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Uncomment to check if admin
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            response.sendRedirect("/login.jsp?error=User not logged in. Please log in.");
            return;
        }
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
            int UserID = (int)session.getAttribute("userId");
            User user = dbm.getUserById(UserID);
            if (user == null || !(user instanceof Model.Users.Staff) || !((Model.Users.Staff) user).isAdmin()) {
                response.sendRedirect("login.jsp?error=You are not authorized to access this page.");
                return;
            }
        }
        catch (SQLException e) {
            response.sendRedirect("register.jsp?error=" +  e.getMessage());
        }
        request.getRequestDispatcher("/userManagement.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBConnector dbc = new DBConnector()) {
            HttpSession session = request.getSession();
            DBManager dbm = new DBManager(dbc.openConnection());
            String selectedUserIDStr = request.getParameter("selectedUserID");
            String activeTab = request.getParameter("activeTab");
            String addUserAction = request.getParameter("addUser");
            String editUserAction = request.getParameter("editUser");
            String deleteUserAction = request.getParameter("deleteUser");
            String activateUserAction = request.getParameter("activateUser");
            String deactivateUserAction = request.getParameter("deactivateUser");
            session.setAttribute("selectedUserID", selectedUserIDStr);
            request.setAttribute("selectedUserID", selectedUserIDStr);
            if (selectedUserIDStr == null || selectedUserIDStr.isEmpty() || (addUserAction == null && editUserAction == null && deleteUserAction == null && activateUserAction == null && deactivateUserAction == null)) {
                response.sendRedirect("userManagement.jsp");
                return;
            }
            if (selectedUserIDStr != null && addUserAction == null) {
                int selectedUserID = Integer.parseInt(selectedUserIDStr);
                User selectedUser = dbm.getUserById(selectedUserID);
                if (selectedUser != null && selectedUser instanceof Model.Users.Staff && ((Model.Users.Staff) selectedUser).isAdmin()) {
                    response.sendRedirect("userManagement.jsp?error=You cannot edit or delete an admin.");
                    return;
                }
            }
            if (activateUserAction != null || deactivateUserAction != null) {
                int selectedUserID = Integer.parseInt(selectedUserIDStr);
                if (activateUserAction != null) {
                    dbm.setUserActivated(selectedUserID, true);
                    response.sendRedirect("userManagement.jsp?success=User activated successfully.");
                } else {
                    dbm.setUserActivated(selectedUserID, false);
                    response.sendRedirect("userManagement.jsp?success=User deactivated successfully.");
                }
                return;
            }
            if (deleteUserAction != null) {
                response.sendRedirect("deleteUserConfirm.jsp");
            }
            if (activeTab.equals("Customer")) {
                if (addUserAction != null) {
                    response.sendRedirect("addCustomer.jsp");
                }
                else if (editUserAction != null) {
                    response.sendRedirect("editCustomer.jsp");
                }
            }
            else if (activeTab.equals("Staff")) {
                if (addUserAction != null) {
                    response.sendRedirect("addStaff.jsp");
                }
                else if (editUserAction != null) {
                    response.sendRedirect("editStaff.jsp");
                }
            }
        }
        catch (SQLException e) {
            response.sendRedirect("userManagement.jsp?error=" +  e.getMessage());
        }
    }
}
