package interfaces;

/*
 * Interface for Classroom
 */

import exceptionhandlers.InvalidDataException;

public interface IClassroom {

    public void setRoomNumber(String p_roomNumber) throws InvalidDataException;
    public void setTypeOfRoom(String p_roomType) throws InvalidDataException;
    public void setCapacity(int p_capacity) throws InvalidDataException;

    public String getRoomNumber();
    public String getTypeOfRoom();
    public int getCapacity();

}