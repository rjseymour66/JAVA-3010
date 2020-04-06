package datamodels;

import exceptionhandlers.InvalidDataException;
import java.time.LocalDate;

public abstract class Person {

    private String name;
    private String address;
    private LocalDate dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String p_name) throws InvalidDataException {
        if (p_name.length() == 0) {
            throw new InvalidDataException("Name required");
        } else {
            name = p_name;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String p_address) throws InvalidDataException {
        if (p_address.length() == 0) {
            throw new InvalidDataException("Address required");
        } else {
            address = p_address;
        }
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate p_dateOfBirth) throws InvalidDataException {
        if (p_dateOfBirth == null)  {
            dateOfBirth = null;
            throw new InvalidDataException("Date of birth not specified, setting to null");
        } else {
            dateOfBirth = p_dateOfBirth;
        }
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", address=" + address
                + ", dateOfBirth=" + dateOfBirth + '}';
    }
}
