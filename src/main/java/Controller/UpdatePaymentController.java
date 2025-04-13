package Controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Model.DB;
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
        User user = DB.getUserById(userId);
        Customer customer = (Customer)user;
        customer.setPaymentInfo(newPaymentInfo);
        session.setAttribute("paymentSuccess", "Payment details updated successfully.");
        
        response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("pdbSystem/updatePaymentMethod.jsp");
    }
}