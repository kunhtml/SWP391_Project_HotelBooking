package model;

/**
 * Hotel model class
 * Represents a hotel in the system
 */
public class Hotel {
    private int hotelID;
    private String hotelName;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String email;
    private String website;
    private int starRating;
    private String description;
    private String imageURL;

    /**
     * Default constructor
     */
    public Hotel() {
    }

    /**
     * Constructor with essential fields
     * @param hotelID Hotel ID
     * @param hotelName Hotel name
     * @param address Hotel address
     * @param city Hotel city
     * @param country Hotel country
     * @param starRating Hotel star rating
     */
    public Hotel(int hotelID, String hotelName, String address, String city, String country, int starRating) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.starRating = starRating;
    }

    // Getters and Setters
    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
