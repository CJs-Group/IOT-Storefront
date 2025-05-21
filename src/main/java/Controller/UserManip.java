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
import Model.Users.Staff;
import Model.Users.User;

@WebServlet("/userManip")
public class UserManip extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String successRedirectPage = "userManagement.jsp";
        String formErrorRedirectPage = "userManagement.jsp";
        try (DBConnector dbc = new DBConnector()) {
            HttpSession session = request.getSession();
            DBManager dbm = new DBManager(dbc.openConnection());
            String selectedUserIDStr = request.getParameter("selectedUserID");
            session.setAttribute("selectedUserID", selectedUserIDStr);
            String formAction = request.getParameter("formAction");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
                switch (formAction) {
                case "addCustomer":
                    formErrorRedirectPage = "addCustomer.jsp";
                    if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
                        session.setAttribute("formError", "Username, Email, and Password are required.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (!Validator.validateEmail(email)) {
                        session.setAttribute("formError", "Invalid email format.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (!Validator.validatePassword(password)) {
                        session.setAttribute("formError", "Password must be at least 4 characters long.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (dbm.doesEmailExist(email)) {
                        session.setAttribute("formError", "Email already exists.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    String hashedPassAddCust = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
                    Customer newCustomer = new Customer(0, username, hashedPassAddCust, email, phone, address, null);
                    dbm.createUser(newCustomer);
                    session.setAttribute("formSuccess", "Customer added successfully.");
                    response.sendRedirect(successRedirectPage);
                    break;

                case "editCustomer":
                    formErrorRedirectPage = "editCustomer.jsp";
                    int custId = Integer.parseInt(selectedUserIDStr);
                    if (username == null || username.isEmpty() || email == null || email.isEmpty()) {
                        session.setAttribute("formError", "Username and Email are required.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (!Validator.validateEmail(email)) {
                        session.setAttribute("formError", "Invalid email format.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    
                    User userToEditCust = dbm.getUserById(custId);
                    if (!(userToEditCust instanceof Customer)) {
                        session.setAttribute("formError", "Selected user is not a customer or not found.");
                        response.sendRedirect("userManagement.jsp");
                        return;
                    }
                    Customer customerToEdit = (Customer) userToEditCust;

                    if (!customerToEdit.getEmail().equalsIgnoreCase(email) && dbm.doesEmailExist(email)) {
                        session.setAttribute("formError", "New email already exists for another user.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    customerToEdit.setUsername(username);
                    customerToEdit.setEmail(email); 
                    customerToEdit.setPhoneNumber(phone);
                    customerToEdit.setAddress(address);
                    if (password != null && !password.isEmpty()) {
                        if (!Validator.validatePassword(password)) {
                            session.setAttribute("formError", "Password must be at least 4 characters long.");
                            response.sendRedirect(formErrorRedirectPage);
                            return;
                        }
                        customerToEdit.setPassword(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
                    }
                    dbm.updateUser(customerToEdit);
                    session.setAttribute("formSuccess", "Customer updated successfully.");
                    response.sendRedirect(successRedirectPage);
                    break;

                case "addStaff":
                    formErrorRedirectPage = "addStaff.jsp";
                    if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
                        session.setAttribute("formError", "Username, Email, and Password are required.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (!Validator.validateEmail(email)) {
                        session.setAttribute("formError", "Invalid email format.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (!Validator.validatePassword(password)) {
                        session.setAttribute("formError", "Password must be at least 4 characters long.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (dbm.doesEmailExist(email)) {
                        session.setAttribute("formError", "Email already exists.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    String hashedPassAddStaff = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
                    // Ensure new staff are NEVER created as admins through this form.
                    Staff newStaff = new Staff(0, username, hashedPassAddStaff, email, phone, false); 
                    dbm.createUser(newStaff);
                    session.setAttribute("formSuccess", "Staff added successfully.");
                    response.sendRedirect(successRedirectPage);
                    break;

                case "editStaff":
                    formErrorRedirectPage = "editStaff.jsp";
                    int staffId = Integer.parseInt(selectedUserIDStr);
                    if (username == null || username.isEmpty() || email == null || email.isEmpty()) {
                        session.setAttribute("formError", "Username and Email are required.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    if (!Validator.validateEmail(email)) {
                        session.setAttribute("formError", "Invalid email format.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }

                    User userToEditStaff = dbm.getUserById(staffId);
                    if (!(userToEditStaff instanceof Staff)) {
                        session.setAttribute("formError", "Selected user is not staff or not found.");
                        response.sendRedirect("userManagement.jsp");
                        return;
                    }
                    Staff staffToEdit = (Staff) userToEditStaff;

                    if (!staffToEdit.getEmail().equalsIgnoreCase(email) && dbm.doesEmailExist(email)) {
                        session.setAttribute("formError", "New email already exists for another user.");
                        response.sendRedirect(formErrorRedirectPage);
                        return;
                    }
                    staffToEdit.setUsername(username);
                    staffToEdit.setEmail(email);
                    staffToEdit.setPhoneNumber(phone);

                    if (password != null && !password.isEmpty()) {
                         if (!Validator.validatePassword(password)) {
                            session.setAttribute("formError", "Password must be at least 4 characters long.");
                            response.sendRedirect(formErrorRedirectPage);
                            return;
                        }
                        staffToEdit.setPassword(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
                    }

                    Staff updatedStaffDetails = new Staff(staffToEdit.getUserID(), 
                                                          staffToEdit.getUsername(), 
                                                          staffToEdit.getPassword(), 
                                                          staffToEdit.getEmail(),    
                                                          staffToEdit.getPhoneNumber(),
                                                          staffToEdit.isAdmin());
                    
                    dbm.updateUser(updatedStaffDetails); 
                    session.setAttribute("formSuccess", "Staff updated successfully.");
                    response.sendRedirect(successRedirectPage);
                    break;
                case "deleteUser":
                    formErrorRedirectPage = "userManagement.jsp";
                    successRedirectPage = "userManagement.jsp";
                    try {
                        int userIdToDelete = Integer.parseInt(selectedUserIDStr);
                        dbm.deleteUser(userIdToDelete);
                        response.sendRedirect(successRedirectPage);
                    }
                    catch (NumberFormatException e) {
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    catch (SQLException e) {
                        session.setAttribute("formError", "Database error during deletion: " + e.getMessage());
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    break;
                default:
                    session.setAttribute("formError", "Invalid action specified: " + formAction);
                    response.sendRedirect("userManagement.jsp");
                    break;
            }
        }
        catch (SQLException e) {
            response.sendRedirect("register.jsp?error=" +  e.getMessage());
        }
        catch (NumberFormatException e) {
            response.sendRedirect("userManagement.jsp?error=Invalid user ID format.");
        }
        catch (Exception e) {
            response.sendRedirect("userManagement.jsp?error=" + e.getMessage());
        }
    }
}
