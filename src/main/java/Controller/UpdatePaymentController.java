package Controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.SQLException;
import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Order.PaymentInfo;
import Model.Users.Customer;
import Model.Users.User;

@WebServlet("/updatePaymentMethod")
public class UpdatePaymentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
            HttpSession session = request.getSession();
            // Clear any previous messages
            session.removeAttribute("paymentErr");
            session.removeAttribute("paymentSuccess");

            String cardNo = request.getParameter("creditCardNumber");
            String fullName = request.getParameter("fullName");
            String expiryDate = request.getParameter("expiryDate");
            String cvvStr = request.getParameter("cvv");

            if (cardNo == null || fullName == null || expiryDate == null || cvvStr == null ||
            cardNo.isEmpty() || fullName.isEmpty() || expiryDate.isEmpty() || cvvStr.isEmpty()) {
                session.setAttribute("paymentErr", "All fields are required.");
                response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
                return;
            }
            
            int cvv = 0;
            try {
                cvv = Integer.parseInt(cvvStr);
            }
            catch(NumberFormatException e) {
                session.setAttribute("paymentErr", "Invalid CVV format.");
                response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
                return;
            }
            
            PaymentInfo newPaymentInfo = new PaymentInfo(1, cardNo, cvv, expiryDate, fullName);
            try {
                if(!newPaymentInfo.validate()) {
                    session.setAttribute("paymentErr", "Invalid payment details. Please re-enter details.");
                    response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
                    return;
                }
            }
            catch(DateTimeParseException e) {
                session.setAttribute("paymentErr", "Expiry date format is invalid. Use MM/yy.");
                response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
                return;
            }
            
            int userId = (int) session.getAttribute("userId");
            User user;
            try {
                dbm.updateCardDetail(newPaymentInfo);
                user = dbm.getUserById(userId);
                Customer customer = (Customer)user;
                customer.setPaymentInfo(newPaymentInfo);
                dbm.updateUser(user);
            }
            catch (SQLException e) {
                session.setAttribute("paymentErr", "User not found.");
                response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
                return;
            }
            session.setAttribute("paymentSuccess", "Payment details updated successfully.");
            
            response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
        }
        catch (SQLException e) {
            response.sendRedirect("pdbSystem/updatePaymentMethod.jsp?error=" + e.getMessage());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
    }
}