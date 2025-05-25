package Controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Order.PaymentInfo;
import Model.Users.Customer;
import Model.Users.User;

@WebServlet("/updatePaymentMethod")
public class UpdatePaymentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            response.sendRedirect("updatePaymentMethod.jsp");
            return;
        }
        
        int cvv = 0;
        try {
            cvv = Integer.parseInt(cvvStr);
        }
        catch(NumberFormatException e) {
            session.setAttribute("paymentErr", "Invalid CVV format.");
            response.sendRedirect("updatePaymentMethod.jsp");
            return;
        }
        
        PaymentInfo newPaymentInfo = new PaymentInfo(1, cardNo, cvv, expiryDate, fullName);
        try {
            if(!newPaymentInfo.validate()) {
                session.setAttribute("paymentErr", "Invalid payment details. Please re-enter details.");
                response.sendRedirect("updatePaymentMethod.jsp");
                return;
            }
        }
        catch(DateTimeParseException e) {
            session.setAttribute("paymentErr", "Expiry date format is invalid. Use MM/yy.");
            response.sendRedirect("updatePaymentMethod.jsp");
            return;
        }
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId != null) {
            try (DBConnector dbc = new DBConnector()) {
                DBManager dbm = new DBManager(dbc.openConnection());
                User user;
                try {
                    dbm.updateCardDetail(newPaymentInfo);
                    user = dbm.getUserById(userId);
                    Customer customer = (Customer)user;
                    customer.setPaymentInfo(newPaymentInfo);
                    dbm.updateUser(user);
                    dbm.createCardDetail(newPaymentInfo, userId);
                }
                catch (SQLException e) {
                    session.setAttribute("paymentErr", "User not found.");
                    response.sendRedirect("updatePaymentMethod.jsp");
                    return;
                }
                session.setAttribute("paymentSuccess", "Payment details updated successfully.");
            }
            catch (SQLException e) {
                session.setAttribute("paymentErr", "Database error: " + e.getMessage());
                response.sendRedirect("updatePaymentMethod.jsp");
                return;
            }
        }
        else {
            List<PaymentInfo> sessionPaymentInfos = new ArrayList<>();
            sessionPaymentInfos.add(newPaymentInfo);
            session.setAttribute("paymentInfos", sessionPaymentInfos);
            session.setAttribute("paymentSuccess", "Payment details saved for this session.");
        }
        
        response.sendRedirect("updatePaymentMethod.jsp");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("updatePaymentMethod.jsp");
    }
}