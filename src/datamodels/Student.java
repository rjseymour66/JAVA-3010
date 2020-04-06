package datamodels;

import controllers.Application;
import exceptionhandlers.InvalidDataException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Person {

    private int studentID;
    private LocalDate dateOfGraduation;
    private float gpa;
    private ArrayList<Course> listOfCourses = new ArrayList<>();

    public Student() {
        Application.getDEBUG_LOGGER().finest("Creating student profile");
    }

    public int getStudentID() {
        return studentID;
    }

    /**
     * This version of setStudentID takes an integer and ensures that the
     * entered value is is between 1000000 and 9999999
     *
     * @param p_studentID
     */
    public void setStudentID(int p_studentID) throws InvalidDataException {

        // Ensure the student id is greater than or equal to 1000000 and 
        // less than or equal to 9999999 - this will ensure 7 characters
        if (p_studentID < 1000000 || p_studentID > 9999999) {
            Application.getDEBUG_LOGGER().warning("Invalid student ID entered");
            throw new InvalidDataException("Invalid Student ID: " + p_studentID);
        } else {
            studentID = p_studentID;
        }
        
    }

    /**
     * This version of setStudentID takes a string and converts to an integer
     * and calls the setStudentID(int). It first checks that the entered String
     * value doesn't start with a zero and only contains numerical digits
     *
     * @param p_studentID
     */
    public void setStudentID(String p_studentID) throws InvalidDataException {
        
        // Check to see if student id is empty or null
        if ( (p_studentID == null) || (p_studentID.length() == 0) ) {
            Application.getDEBUG_LOGGER().warning("Student ID not specified");
            throw new InvalidDataException("Student ID not specified");
        }
        
        // Check to see if student id starts with zero
        if (p_studentID.startsWith("0")) {
            Application.getDEBUG_LOGGER().warning("Student ID started with 0");
            throw new InvalidDataException("Student ID can not start with zero: " + p_studentID);
        }
        
        // Check to see if student id is 7 characters in length
        if (p_studentID.length() != 7) {
            Application.getDEBUG_LOGGER().warning("Student ID not 7 characters");
            throw new InvalidDataException("Student ID must be 7 characters in length " + p_studentID);
        }
        
        // If we pass all the previous tests, try to parse the string into an interger
        // If unparsable, a parsing error will be thrown.  
        // Catch the error and throw an InvalidDataException
        try {
            studentID = Integer.parseInt(p_studentID);
            setStudentID(studentID);
        } catch (NumberFormatException exp) {
            Application.getDEBUG_LOGGER().warning("Invalid student ID");
            throw new InvalidDataException("Invalid Student ID: " + p_studentID);
        }
    }

    public LocalDate getDateOfGraduation() {
        return dateOfGraduation;
    }

    public void setDateOfGraduation(LocalDate p_dateOfGraduation) throws InvalidDataException {
        if (p_dateOfGraduation == null) {
            dateOfGraduation = null;
            Application.getDEBUG_LOGGER().warning("Invalid date of graduation");
            Application.getDEBUG_LOGGER().info("Date of graduation set to null");
            throw new InvalidDataException("Invalid date of graduation, setting to null");
        } else {
            dateOfGraduation = p_dateOfGraduation;
        }
    }

    public float getGPA() {
        return gpa;
    }

    public void setGPA(float p_gpa) throws InvalidDataException {
        if (p_gpa > 0) {
            gpa = p_gpa;
        } else {
            gpa = 0.0f;
            throw new InvalidDataException("Invalid GPA, setting to 0.0");
        }
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }

    @Override
    public String toString() {
        return "Student{name=" + super.getName() + ", address=" + super.getAddress()
                + ", dateOfBirth=" + super.getDateOfBirth() + ", studentID=" + studentID
                + ", dateOfGraduation=" + dateOfGraduation + ", gpa=" + gpa
                + ", listOfCourses=" + listOfCourses + '}';
    }

}
