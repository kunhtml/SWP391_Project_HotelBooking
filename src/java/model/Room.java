package model;

/**
 * Room model class
 * Represents a room in a hotel
 */
public class Room {
    private int roomID;
    private String roomNumber;
    private int hotelID;
    private int roomTypeId;
    private int floor;
    private String status;
    private RoomType roomType;

    /**
     * Default constructor
     */
    public Room() {
    }

    /**
     * Constructor with essential fields
     * @param roomID Room ID
     * @param roomNumber Room number
     * @param hotelID Hotel ID
     * @param roomTypeId Room type ID
     * @param floor Floor number
     * @param status Room status
     */
    public Room(int roomID, String roomNumber, int hotelID, int roomTypeId, int floor, String status) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.hotelID = hotelID;
        this.roomTypeId = roomTypeId;
        this.floor = floor;
        this.status = status;
    }

    // Getters and Setters
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
