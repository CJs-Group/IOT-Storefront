package Model.DAO;

import Model.Order.PaymentInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDetailDAO {
    private Connection conn;

    public CardDetailDAO(Connection conn) {
        this.conn = conn;
    }

    private PaymentInfo resultToCardDetail(ResultSet rs) throws SQLException {
        int cardID = rs.getInt("CardID");
        String cardNo = rs.getString("CardNo");
        String ccvStr = rs.getString("CCV");
        String cardExpr = rs.getString("CardExpr");
        String cardHolderName = rs.getString("CardHolderName");

        int ccv = 0;
        try {
            ccv = Integer.parseInt(ccvStr);
        }
        catch (NumberFormatException e) {
            System.err.println("Warning: CCV from DB is not a valid integer: " + ccvStr);
        }
        String expiryDateForModel = cardExpr.length() == 4 ? cardExpr.substring(0, 2) + "/" + cardExpr.substring(2) : cardExpr;
        PaymentInfo cardDetail = new PaymentInfo(cardID, cardNo, ccv, expiryDateForModel, cardHolderName);
        return cardDetail;
    }

    public void createCardDetail(PaymentInfo cardDetail, int userId) throws SQLException {
        String sql = "INSERT INTO CardDetails (UserID, CardNo, CCV, CardExpr, CardHolderName) VALUES (?, ?, ?, ?, ?) RETURNING CardID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, cardDetail.getCardNo());
            ps.setString(3, String.valueOf(cardDetail.getCCV()));
            String cardExprForDB = cardDetail.getExpiryDate().replace("/", "");
            ps.setString(4, cardExprForDB);

            ps.setString(5, cardDetail.getCardHolderName());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cardDetail.setPaymentId(rs.getInt(1));
                }
                else {
                    throw new SQLException("Creating card detail failed, no ID obtained.");
                }
            }
        }
    }

    public PaymentInfo getCardDetailById(int cardId) throws SQLException {
        String sql = "SELECT * FROM CardDetails WHERE CardID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cardId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToCardDetail(rs);
                }
            }
        }
        return null;
    }

    public List<PaymentInfo> getCardDetailsByUserId(int userId) throws SQLException {
        List<PaymentInfo> cardDetails = new ArrayList<>();
        String sql = "SELECT * FROM CardDetails WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cardDetails.add(resultToCardDetail(rs));
                }
            }
        }
        return cardDetails;
    }

    public void updateCardDetail(PaymentInfo cardDetail) throws SQLException {
        String sql = "UPDATE CardDetails SET CardNo = ?, CCV = ?, CardExpr = ?, CardHolderName = ? WHERE CardID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cardDetail.getCardNo());
            ps.setString(2, String.valueOf(cardDetail.getCCV()));

            String cardExprForDB = cardDetail.getExpiryDate().replace("/", "");
            ps.setString(3, cardExprForDB);

            ps.setString(4, cardDetail.getCardHolderName());
            ps.setInt(5, cardDetail.getPaymentId());
            ps.executeUpdate();
        }
    }

    public void deleteCardDetail(int cardId) throws SQLException {
        String sql = "DELETE FROM CardDetails WHERE CardID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cardId);
            ps.executeUpdate();
        }
    }
}