package datamodels;

import controllers.Application;
import exceptionhandlers.InvalidDataException;
import interfaces.IClassroom;

public class Classroom implements IClassroom {

    private String roomNumber = "XXX";
    private String typeOfRoom = "UNKNOWN";
    private int capacity;

    // Add a constructor that will log an audit message when a classroom is created
    public Classroom() {
        Application.getDEBUG_LOGGER().finest("Creating classroom");
    }

    public void setRoomNumber(String p_roomNumber) throws InvalidDataException {
        // Test for valid room number
        if (p_roomNumber.length() == 0) {
            Application.getDEBUG_LOGGER().warning("No room number specified");
            throw new InvalidDataException("No room number specified");
        }
        if (!p_roomNumber.matches("^[a-zA-Z]{2}[0-9]{3}$")) {
            Application.getDEBUG_LOGGER().warning("Invalid room number");
            throw new InvalidDataException("Invalid room number");
        }
        // If valid, set room number
        this.roomNumber = p_roomNumber;
    }

    public void setTypeOfRoom(String p_typeOfRoom) throws InvalidDataException {
        // Test for empty string
        if (p_typeOfRoom.length() == 0) {
            Application.getDEBUG_LOGGER().warning("No room type specified");
            throw new InvalidDataException("No room type specified");
        }
        // Test for invalid string
        if ((!p_typeOfRoom.equals("LAB")) && (!p_typeOfRoom.equals("CLASSROOM"))
                && (!p_typeOfRoom.equals("LECTURE HALL"))) {
            Application.getDEBUG_LOGGER().warning("Invalid room type specified");
            throw new InvalidDataException("Invalid room type specified");
        }
        // If valid, set room type
        typeOfRoom = p_typeOfRoom;
    }

    public void setCapacity(int p_capacity) throws InvalidDataException {

        // Test for valid value 
        if (p_capacity <= 0) {
            Application.getDEBUG_LOGGER().warning("Invalid room capacity entered");
            throw new InvalidDataException("Room capacity must be greater than 0");
        }
        // If valid, set room capacity
        capacity = p_capacity;

    }
    
    public void setCapacity(String p_capacity) throws InvalidDataException {

        // Test for valid value 
        try {
            Integer.parseInt(p_capacity);
        } catch (Exception exp) {
            Application.getDEBUG_LOGGER().warning("Invalid room capacity entered");
            throw new InvalidDataException("Room capacity is not a valid value");
        }

        setCapacity(p_capacity);
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Classroom{" + "roomNumber=" + roomNumber + ", typeOfRoom="
                + typeOfRoom + ", capacity=" + capacity + '}';
    }

}
