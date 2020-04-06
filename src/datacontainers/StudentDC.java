/**
 * This Class contains a list that holds faculty objects created in the UI.
 */

package datacontainers;

import datamodels.Student;

import java.util.ArrayList;

public class StudentDC {

    private ArrayList<Student> listOfStudents = new ArrayList<>();

    /**
     * no-arg constructor
     */
    public StudentDC() {
    }

    public ArrayList<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(ArrayList<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }
}
