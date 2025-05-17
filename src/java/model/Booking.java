package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Booking model class
 * Represents a booking in the system
 */
public class Booking {
    private int bookingID;
    private int customerID;
    private int roomID;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfGuests;
    private Timestamp bookingDate;
    private double totalPrice;
    private String bookingStatus;
    private String specialRequests;
    private Room room;
    private User customer;

    /**
     * Default constructor
     */
    public Booking() {
    }

    /**
     * Constructor with essential fields
     * @param bookingID Booking ID
     * @param customerID Customer ID
     * @param roomID Room ID
     * @param checkInDate Check-in date
     * @param checkOutDate Check-out date
     * @param numberOfGuests Number of guests
     * @param totalPrice Total price
     * @param bookingStatus Booking status
     */
    public Booking(int bookingID, int customerID, int roomID, Date checkInDate, Date checkOutDate, 
                  int numberOfGuests, double totalPrice, String bookingStatus) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.roomID = roomID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
        this.bookingDate = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
