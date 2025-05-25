package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Order.Payment;
import Model.Order.PaymentInfo;

@WebServlet("/paymentManagement")
public class PaymentController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userIdObj = (Integer) session.getAttribute("userId");
        
        if (userIdObj == null) {
            response.sendRedirect("login.jsp?error=Please login to view payment history");
            return;
        }
        
        int userId = userIdObj;
        
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
            
            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                handleDeletePaymentMethod(request, response, dbm, userId);
                return;
            }
            
            // handling search parameters
            String paymentIdStr = request.getParameter("paymentId");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            
            List<Payment> payments;
            if (paymentIdStr != null || startDateStr != null || endDateStr != null) {
                // searching with criteria
                Integer paymentId = null;
                Date startDate = null;
                Date endDate = null;
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                try {
                    if (paymentIdStr != null && !paymentIdStr.trim().isEmpty()) {
                        paymentId = Integer.parseInt(paymentIdStr);
                    }
                    if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                        startDate = sdf.parse(startDateStr);
                    }
                    if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                        endDate = sdf.parse(endDateStr);
                        // setting end date to end of day
                        endDate = new Date(endDate.getTime() + 24 * 60 * 60 * 1000 - 1);
                    }
                } catch (NumberFormatException | ParseException e) {
                    request.setAttribute("error", "Invalid search criteria format");
                }
                
                payments = dbm.searchPaymentsByUserIdAndCriteria(userId, paymentId, startDate, endDate);
            } else {
                // getting all payments for user
                payments = dbm.getPaymentsByUserId(userId);
            }
            
            // getting user's saved payment methods for management
            List<PaymentInfo> savedPaymentMethods = dbm.getCardDetailsByUserId(userId);
            
            request.setAttribute("payments", payments);
            request.setAttribute("savedPaymentMethods", savedPaymentMethods);
            request.getRequestDispatcher("/pdbSystem/paymentHistory.jsp").forward(request, response);
            
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pdbSystem/paymentHistory.jsp").forward(request, response);
        }
    }
    
    private void handleDeletePaymentMethod(HttpServletRequest request, HttpServletResponse response, 
                                         DBManager dbm, int userId) throws ServletException, IOException {
        String cardIdStr = request.getParameter("cardId");
        if (cardIdStr == null || cardIdStr.trim().isEmpty()) {
            response.sendRedirect("paymentManagement?error=Invalid payment method ID");
            return;
        }
        
        try {
            int cardId = Integer.parseInt(cardIdStr);
            
            // verifying this card belongs to the user
            List<PaymentInfo> userCards = dbm.getCardDetailsByUserId(userId);
            boolean cardBelongsToUser = userCards.stream()
                .anyMatch(card -> card.getPaymentId() == cardId);
            
            if (!cardBelongsToUser) {
                response.sendRedirect("paymentManagement?error=Unauthorized access to payment method");
                return;
            }
            
            // checking if card is used in any pending payments
            dbm.deleteCardDetail(cardId);
            response.sendRedirect("paymentManagement?success=Payment method deleted successfully");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("paymentManagement?error=Invalid payment method ID format");
        } catch (SQLException e) {
            response.sendRedirect("paymentManagement?error=Error deleting payment method: " + e.getMessage());
        }
    }
}