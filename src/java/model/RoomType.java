package model;

/**
 * RoomType model class
 * Represents a type of room in a hotel
 */
public class RoomType {
    private int id;
    private String name;
    private String description;
    private double price;
    private int capacity;
    private int area;
    private String imageUrl;
    private int availableRooms;

    /**
     * Default constructor
     */
    public RoomType() {
    }

    /**
     * Constructor with essential fields
     * @param id Room type ID
     * @param name Room type name
     * @param description Room type description
     * @param price Room type price
     * @param capacity Room capacity
     * @param area Room area in square meters
     * @param imageUrl URL to room image
     */
    public RoomType(int id, String name, String description, double price, int capacity, int area, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.area = area;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }
}
