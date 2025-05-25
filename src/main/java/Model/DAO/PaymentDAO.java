package Model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import Model.Order.Payment;
import Model.Order.Payment.PaymentStatus;
import Model.Order.PaymentInfo;

public class PaymentDAO {
    private Connection conn;
    private CardDetailDAO cardDetailDAO;

    public PaymentDAO(Connection conn, CardDetailDAO cardDetailDAO) {
        this.conn = conn;
        this.cardDetailDAO = cardDetailDAO;
    }

    private Payment resultToPayment(ResultSet rs) throws SQLException {
        int paymentID = rs.getInt("PaymentID");
        int orderID = rs.getInt("OrderID");
        int cardID = rs.getInt("CardID");
        int amount = rs.getInt("Amount");
        Timestamp paymentDateTimestamp = rs.getTimestamp("PaymentDate");
        String statusStr = rs.getString("PaymentStatus");

        Date paymentDate = (paymentDateTimestamp != null) ? new Date(paymentDateTimestamp.getTime()) : null;
        PaymentStatus paymentStatus = PaymentStatus.fromString(statusStr);

        Payment payment = new Payment(paymentID, orderID, cardID, amount, paymentDate, paymentStatus);
        
        // loading payment info for display
        PaymentInfo paymentInfo = cardDetailDAO.getCardDetailById(cardID);
        payment.setPaymentInfo(paymentInfo);
        
        return payment;
    }

    public void createPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payments (OrderID, CardID, Amount, PaymentDate, PaymentStatus) VALUES (?, ?, ?, ?, ?) RETURNING PaymentID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payment.getOrderID());
            ps.setInt(2, payment.getCardID());
            ps.setInt(3, payment.getAmount());
            ps.setTimestamp(4, (payment.getPaymentDate() != null) ? new Timestamp(payment.getPaymentDate().getTime()) : new Timestamp(System.currentTimeMillis()));
            ps.setString(5, payment.getPaymentStatus().name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    payment.setPaymentID(rs.getInt(1));
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }
        }
    }

    public Payment getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToPayment(rs);
                }
            }
        }
        return null;
    }

    public List<Payment> getPaymentsByUserId(int userId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = """
            SELECT p.* FROM Payments p
            JOIN Orders o ON p.OrderID = o.OrderID
            WHERE o.UserID = ?
            ORDER BY p.PaymentDate DESC
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(resultToPayment(rs));
                }
            }
        }
        return payments;
    }

    public List<Payment> searchPaymentsByUserIdAndCriteria(int userId, Integer paymentId, Date startDate, Date endDate) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("""
            SELECT p.* FROM Payments p
            JOIN Orders o ON p.OrderID = o.OrderID
            WHERE o.UserID = ?
        """);
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (paymentId != null) {
            sqlBuilder.append(" AND p.PaymentID = ?");
            params.add(paymentId);
        }
        if (startDate != null) {
            sqlBuilder.append(" AND p.PaymentDate >= ?");
            params.add(new Timestamp(startDate.getTime()));
        }
        if (endDate != null) {
            sqlBuilder.append(" AND p.PaymentDate <= ?");
            params.add(new Timestamp(endDate.getTime()));
        }
        sqlBuilder.append(" ORDER BY p.PaymentDate DESC");

        try (PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(resultToPayment(rs));
                }
            }
        }
        return payments;
    }

    public void updatePaymentStatus(int paymentId, PaymentStatus newStatus) throws SQLException {
        String sql = "UPDATE Payments SET PaymentStatus = ? WHERE PaymentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus.name());
            ps.setInt(2, paymentId);
            ps.executeUpdate();
        }
    }

    public void deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        }
    }
}