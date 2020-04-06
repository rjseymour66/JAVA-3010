package datamodels;

import controllers.Application;
import exceptionhandlers.InvalidDataException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Faculty extends Person {

    private LocalDate dateOfHire;
    private double salary;
    private String status;
    private ArrayList<Course> listOfCourses = new ArrayList<>();

    // Add a constructor that will log an audit message when a classroom is created
    public Faculty() {
        Application.getDEBUG_LOGGER().fine("Creating faculty profile");
    }

    public LocalDate getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(LocalDate p_dateOfHire) {
        dateOfHire = p_dateOfHire;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double p_salary) throws InvalidDataException {
        if (p_salary <= 0) {
            salary = 0;
            Application.getDEBUG_LOGGER().warning("Invalid salary entered");
            Application.getDEBUG_LOGGER().info("Salary set to 0");
            throw new InvalidDataException("Invalid Salary, setting to $0");
        } else {
            salary = p_salary;
        };
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String p_status) throws InvalidDataException{
        if ((p_status == null) || (p_status.length() == 0)) {
            status = "Full Time";
            Application.getDEBUG_LOGGER().warning("Invalid status entered");
            Application.getDEBUG_LOGGER().info("Set status to full time");
            throw new InvalidDataException("Invalid status, setting to full time");
        } else {
            status = p_status;
        }
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }

    @Override
    public String toString() {
        return "Faculty{name=" + super.getName() + ", address=" + super.getAddress()
                + ", dateOfBirth=" + super.getDateOfBirth() + ", dateOfHire="
                + dateOfHire + ", salary=" + salary + ", status="
                + status + ", listOfCourses=" + listOfCourses + '}';
    }

}
