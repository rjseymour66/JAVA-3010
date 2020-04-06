package datamodels;

import controllers.Application;
import exceptionhandlers.InvalidDataException;
import interfaces.IClassroom;
import interfaces.ICourse;

public class Course implements ICourse {

    private String courseID;
    private String courseName;
    private Classroom classroom = new Classroom();

    // Add a constructor that will log an audit message when a course is created
    public Course() {
        Application.getDEBUG_LOGGER().fine("Creating course");
    }

    public String getCourseID() {
        return courseID;
    }

    /**
     * This method will set a course ID only if it meets the course ID
     * requirements
     * 
     * @param p_courseID
     * @throws InvalidDataException 
     */
    public void setCourseID(String p_courseID) throws InvalidDataException {
        if (p_courseID.length() <= 0) {
            Application.getDEBUG_LOGGER().warning("Course ID missing");
            throw new InvalidDataException("Course ID missing");
        }

        if (!p_courseID.matches("^[a-zA-Z]{4}[0-9]{3}$")) {
            Application.getDEBUG_LOGGER().warning("Invalid course ID");
            throw new InvalidDataException("Invalid course ID");
        }
        // If valid, set course id
        courseID = p_courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String p_courseName) {
        this.courseName = p_courseName;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * This method will set a temporary default classroom if p_classroom is null
     * Note that a default classroom won't contain valid data because we aren't 
     * calling the set methods for classroom so the default data won't meet
     * the requirements for classroom data
     * 
     * @param p_classroom 
     */
    public void setClassroom(Classroom p_classroom) {
        if (p_classroom == null) {
            this.classroom = new Classroom();
        } else {
            this.classroom = p_classroom;
        }
    }

    @Override
    public String toString() {
        return "Course{" + "courseID=" + courseID + ", courseName="
                + courseName + ", classroom=" + classroom + '}';
    }

}
