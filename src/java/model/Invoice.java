package model;

import java.sql.Timestamp;

/**
 * Invoice model class
 * Represents an invoice in the system
 */
public class Invoice {
    private int invoiceID;
    private int bookingID;
    private double totalAmount;
    private double tax;
    private double additionalFees;
    private String paymentStatus;
    private Timestamp createdDate;
    private Booking booking;

    /**
     * Default constructor
     */
    public Invoice() {
    }

    /**
     * Constructor with essential fields
     * @param invoiceID Invoice ID
     * @param bookingID Booking ID
     * @param totalAmount Total amount
     * @param tax Tax amount
     * @param additionalFees Additional fees
     * @param paymentStatus Payment status
     */
    public Invoice(int invoiceID, int bookingID, double totalAmount, double tax, double additionalFees, String paymentStatus) {
        this.invoiceID = invoiceID;
        this.bookingID = bookingID;
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.additionalFees = additionalFees;
        this.paymentStatus = paymentStatus;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getAdditionalFees() {
        return additionalFees;
    }

    public void setAdditionalFees(double additionalFees) {
        this.additionalFees = additionalFees;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Calculate the grand total including tax and additional fees
     * @return Grand total
     */
    public double getGrandTotal() {
        return totalAmount + tax + additionalFees;
    }
}
