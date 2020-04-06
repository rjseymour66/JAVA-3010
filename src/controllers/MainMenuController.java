/*
 * Listens for events on the menu form.
 * Implements the ActionListener interface which contains a single method,
 * "actionPerformed"
 */
package controllers;

import static controllers.Application.getDEBUG_LOGGER;

import java.awt.event.ActionListener;
import java.util.logging.Level;

import datacontainers.ClassroomDC;
import datacontainers.CourseDC;
import datacontainers.FacultyDC;
import datacontainers.StudentDC;
import utilities.io.ClassroomIO;

import utilities.io.CourseIO;
import utilities.io.FacultyIO;
import utilities.io.StudentIO;
import view.MainMenu;

public class MainMenuController implements ActionListener {

    String persistedFileLocation;

    /**
     * Constructor
     * @param persistedFileLocation
     */
    public MainMenuController(String persistedFileLocation) {
        this.persistedFileLocation = persistedFileLocation;
    }

    // The data models are instantiated here and passed to the
    // constructors for the controllers
    ClassroomDC classroomDataContainer = new ClassroomDC();
    CourseDC courseDataContainer = new CourseDC();
    FacultyDC facultyDataContainer = new FacultyDC();
    StudentDC studentDataContainer = new StudentDC();

    // The main menu form gets created here. Notice it takes this controller object
    // as an argument to the constructor
    private MainMenu mainMenu = new MainMenu(this);

    /**
     * The ActionListener interface contains a single method, actionPerformed
     */
    public void actionPerformed(java.awt.event.ActionEvent event) {

        //  Figure out which button was clicked
        String menuItemClicked = event.getActionCommand();

        // create the controller which will open the correct form
        if (menuItemClicked.equals("Add Classroom")) {
            InputClassroomFormController inputController = new InputClassroomFormController(classroomDataContainer);
        } else if (menuItemClicked.equals("List Classrooms")) {
            ReportClassroomController reportController = new ReportClassroomController(classroomDataContainer);
        } else if (menuItemClicked.equals("Add Course")) {
            InputCourseFormController inputController = new InputCourseFormController(courseDataContainer, classroomDataContainer);
        } else if (menuItemClicked.equals("List Courses")) {
            ReportCourseController reportController = new ReportCourseController(courseDataContainer);
        } else if (menuItemClicked.equals("Add Faculty")) {
            InputFacultyFormController inputController = new InputFacultyFormController(facultyDataContainer, courseDataContainer);
        } else if (menuItemClicked.equals("List Faculty")) {
            ReportFacultyController reportController = new ReportFacultyController(facultyDataContainer);
        } else if (menuItemClicked.equals("Add Student")) {
            InputStudentFormController inputController = new InputStudentFormController(studentDataContainer, courseDataContainer);
        } else if (menuItemClicked.equals("List Students")) {
            ReportStudentController reportController = new ReportStudentController(studentDataContainer);
        } else if (menuItemClicked.equals("Exit")) {
            System.exit(0);
        } else if (menuItemClicked.equals("Save Data")) {
            ClassroomIO.writeJSONFile(persistedFileLocation, classroomDataContainer);
            CourseIO.writeJSONFile(persistedFileLocation, courseDataContainer);
            FacultyIO.writeJSONFile(persistedFileLocation, facultyDataContainer);
            StudentIO.writeJSONFile(persistedFileLocation, studentDataContainer);
        } else if (menuItemClicked.equals("Load Data")) {
            classroomDataContainer.setListOfClassrooms(ClassroomIO.readJSONFile(persistedFileLocation));
            courseDataContainer.setListOfCourses(CourseIO.readJSONFile(persistedFileLocation));
            facultyDataContainer.setListOfFaculty(FacultyIO.readJSONFile(persistedFileLocation));
            studentDataContainer.setListOfStudents(StudentIO.readJSONFile(persistedFileLocation));
        } else if (menuItemClicked.equals("Log Warning")) {
            getDEBUG_LOGGER().setLevel(Level.WARNING);
        } else if (menuItemClicked.equals("Log Info")) {
            getDEBUG_LOGGER().setLevel(Level.INFO);
        } else if (menuItemClicked.equals("Log Severe")) {
            getDEBUG_LOGGER().setLevel(Level.SEVERE);
        } else if (menuItemClicked.equals("Log All")) {
            getDEBUG_LOGGER().setLevel(Level.ALL);
        }
    }

    // Getter used in the Application.java class
    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
