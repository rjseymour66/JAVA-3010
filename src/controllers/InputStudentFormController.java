/*
 * Listens for events on the input form.
 * Implements the ActionListener interface which contains a single method,
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers;

import datamodels.Student;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import datacontainers.*;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import java.time.LocalDate;
import java.time.Month;
import view.inputforms.StudentInputForm;

public class InputStudentFormController implements ActionListener {

    // The data datacontainers are passed in
    StudentDC studentDC;
    CourseDC courseDC;

    // Input form is created here
    StudentInputForm form;

    // Create a new Student object used in the event methods
    Student newStudent;

    public InputStudentFormController(StudentDC studentDC,
                                      CourseDC courseDC) {

        // store local pointers to the data datacontainers passed in
        this.studentDC = studentDC;
        this.courseDC = courseDC;

        // create the form, pass it this controller
        form = new StudentInputForm(this);

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Save")) {
            this.saveData();
        } else if (event.getActionCommand().equals("Clear")) {
            this.clearForm();
        } else if (event.getActionCommand().equals("Close")) {
            this.closeForm();
        }
    }

    /**
     * Private method to save all the data on the form and create a new student
     * object
     */
    public void saveData() {

        // Create a new Student object used in the event methods
        newStudent = new Student();

        try {

            // Retrieve data from all text fields and store directly into object
            newStudent.setName(form.getNameField().getText());

            newStudent.setAddress(form.getAddressField().getText());

            String studentIdString = form.getStudentIdField().getText();

            newStudent.setStudentID(studentIdString);

            // Retrieve GPA and convert to a double before storing in object
            // If there is a problem converting to a double, it will throw a built in
            // runtime exception which we will catch here and handle it by storing a
            // GPA of zero.
            // An example of testing in the form code and not the data model
            String gpastring = form.getGpaField().getText();


            try {
                float gpadouble = Float.parseFloat(gpastring);

                if (gpadouble < 0.0) {
                    Application.getDEBUG_LOGGER().warning("Invalid GPA");
                    Application.getDEBUG_LOGGER().info("GPA set to 0.0");
                    new ErrorPopup(form, new InvalidDataException("Invalid GPA, setting to 0.0."));
                    newStudent.setGPA(0);
                } else {
                    newStudent.setGPA(gpadouble);
                }
            } catch (NumberFormatException exp) {
                Application.getDEBUG_LOGGER().warning("Invalid GPA. Set to 0.0.");
                Application.getDEBUG_LOGGER().info("GPA set to 0.0");
                new ErrorPopup(form, new InvalidDataException("Invalid GPA, setting to 0.0."));
            }

            // We retrieve the date data and convert to a LocalDate object
            LocalDate dobDate = this.getDate(form.getDateOfBirthFormattedTextField1().getText());

            // Store
            newStudent.setDateOfBirth(dobDate);

            LocalDate gradDate = this.getDate(form.getDateOfGraduationFormattedTextField().getText());

            if (gradDate.isBefore(dobDate)) {
                Application.getDEBUG_LOGGER().warning("Invalid birthdate");
                Application.getDEBUG_LOGGER().info("Birthdate not saved");
                throw new InvalidDataException("Date of graduation cannot be before date of birth.\nInput was not saved.");
            }

            newStudent.setDateOfGraduation(gradDate);

            // Store
            this.studentDC.getListOfStudents().add(newStudent);

        } catch (InvalidDataException exp) {
            new ErrorPopup(form, exp);
        }
    }

    /**
     * Private method to clear the data
     */
    private void clearForm() {
        // The text fields are set to blank
        form.getNameField().setText("");
        form.getStudentIdField().setText("");
        form.getAddressField().setText("");
        form.getGpaField().setText("");
        form.getDateOfBirthFormattedTextField1().setText("");
        form.getDateOfGraduationFormattedTextField().setText("");
    }

    /**
     * Private method to close the form
     */
    private void closeForm() {
        form.dispose();
    }

    //  The following methods are used by the form, you don't need to know how 
    //  these work but if you want to see how form data is transformed into 
    //  actual data types from strings, it's worth a read
    private LocalDate getDate(String formStringDate) {

        // Try and parse the date values.  For student, we don't use spinners so
        // the user needs to enter values.  If they are not valid, set a default date
        // but let the user know.
        String[] dateElements = formStringDate.split("-");
        try {
            Integer dobMonth = Integer.parseInt(dateElements[0]);
            if (dobMonth < 1 || dobMonth > 12) {
                throw new NumberFormatException();
            }
            Integer dobDay = Integer.parseInt(dateElements[1]);
            if (dobDay > this.getNumberOfDaysInMonth(dobMonth)) {
                throw new NumberFormatException();
            }
            Integer dobYear = Integer.parseInt(dateElements[2]);
            if (dobYear > 2020) {
                throw new NumberFormatException();
            }
            LocalDate newdate = LocalDate.of(dobYear, dobMonth, dobDay);
            return newdate;
        } catch (NumberFormatException exp) {
            // Bad date, set a default
            Application.getDEBUG_LOGGER().warning("Invalid date entered");
            Application.getDEBUG_LOGGER().info("Default date set");
            new ErrorPopup(form, new InvalidDataException("Bad date, setting default date"));
            LocalDate newdate = LocalDate.of(1970, Month.JANUARY, 1);
            return newdate;
        }
    }

    private int getNumberOfDaysInMonth(int month) {
        if (month == 1) {
            return 31;
        } else if (month == 2) {
            return 28;
        } else if (month == 3) {
            return 31;
        } else if (month == 4) {
            return 30;
        } else if (month == 5) {
            return 31;
        } else if (month == 6) {
            return 30;
        } else if (month == 7) {
            return 31;
        } else if (month == 8) {
            return 31;
        } else if (month == 9) {
            return 30;
        } else if (month == 10) {
            return 31;
        } else if (month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

}
