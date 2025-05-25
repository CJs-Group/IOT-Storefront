package Model.Order;

import java.util.Date;

public class Payment {
    private int paymentID;
    private int orderID;
    private int cardID;
    private int amount; // Amount in cents
    private Date paymentDate;
    private PaymentStatus paymentStatus;
    private PaymentInfo paymentInfo; // For display purposes

    public enum PaymentStatus {
        Pending,
        Completed,
        Failed,
        Cancelled;

        public static PaymentStatus fromString(String text) {
            if (text != null) {
                for (PaymentStatus status : PaymentStatus.values()) {
                    if (text.equalsIgnoreCase(status.name())) {
                        return status;
                    }
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found in PaymentStatus enum");
        }
    }

    public Payment(int orderID, int cardID, int amount, PaymentStatus paymentStatus) {
        this.orderID = orderID;
        this.cardID = cardID;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = new Date();
    }

    public Payment(int paymentID, int orderID, int cardID, int amount, Date paymentDate, PaymentStatus paymentStatus) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.cardID = cardID;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    // getters and setters
    public int getPaymentID() { return paymentID; }
    public void setPaymentID(int paymentID) { this.paymentID = paymentID; }
    
    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    
    public int getCardID() { return cardID; }
    public void setCardID(int cardID) { this.cardID = cardID; }
    
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public PaymentInfo getPaymentInfo() { return paymentInfo; }
    public void setPaymentInfo(PaymentInfo paymentInfo) { this.paymentInfo = paymentInfo; }
}