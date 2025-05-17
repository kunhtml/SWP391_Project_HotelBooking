package model;

/**
 * Service model class
 * Represents a service offered by a hotel
 */
public class Service {
    private int serviceID;
    private int hotelID;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean isActive;

    /**
     * Default constructor
     */
    public Service() {
    }

    /**
     * Constructor with essential fields
     * @param serviceID Service ID
     * @param hotelID Hotel ID
     * @param name Service name
     * @param description Service description
     * @param price Service price
     * @param category Service category
     */
    public Service(int serviceID, int hotelID, String name, String description, double price, String category) {
        this.serviceID = serviceID;
        this.hotelID = hotelID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isActive = true;
    }

    // Getters and Setters
    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
