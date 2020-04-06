package interfaces;

import datamodels.Classroom;
import exceptionhandlers.InvalidDataException;

/**
 * Interface for Course
 */

public interface ICourse {

    public void setCourseID(String courseID) throws InvalidDataException;
    public void setCourseName(String courseName);
    public void setClassroom(Classroom classroom);

    public String getCourseID();
    public String getCourseName();
    public Classroom getClassroom() throws InvalidDataException;
}
