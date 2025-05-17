package model;

import java.sql.Timestamp;

/**
 * Payment model class
 * Represents a payment in the system
 */
public class Payment {
    private int paymentID;
    private int invoiceID;
    private String paymentMethod;
    private double amount;
    private Timestamp paymentDate;
    private String status;
    private String transactionCode;
    private Invoice invoice;

    /**
     * Default constructor
     */
    public Payment() {
    }

    /**
     * Constructor with essential fields
     * @param paymentID Payment ID
     * @param invoiceID Invoice ID
     * @param paymentMethod Payment method
     * @param amount Payment amount
     * @param status Payment status
     * @param transactionCode Transaction code
     */
    public Payment(int paymentID, int invoiceID, String paymentMethod, double amount, String status, String transactionCode) {
        this.paymentID = paymentID;
        this.invoiceID = invoiceID;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.transactionCode = transactionCode;
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
