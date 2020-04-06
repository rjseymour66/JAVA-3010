package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.MainMenuController;
import datacontainers.CourseDC;
import datamodels.Classroom;
import datamodels.Course;
import exceptionhandlers.ErrorPopup;

import java.io.*;
import java.util.ArrayList;

public class CourseIO {

    /**
     * Constructor is declared private because the IO classes are utilities that
     * contain static methods and should never be instantiated.
     */
    private CourseIO() {
    }

    /**
     * Writes out the course data in JSON format containing all courses in
     * the course data model.
     */
    public static void writeJSONFile(String fileLocation, CourseDC datacontainer) {


        try (PrintWriter jsonFile = new PrintWriter(fileLocation + "course.json")) {
            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // Convert classroom list to JSON format
            gson.toJson(datacontainer.getListOfCourses(), jsonFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Reads a JSON formatted file of classrooms and returns an array list of
     * classrooms.
     *
     */
    public static ArrayList<Course> readJSONFile(String fileLocation) {

        ArrayList<Course> listOfCourses = new ArrayList<>();

        try (BufferedReader jsonFile = new BufferedReader(new FileReader(fileLocation + "course.json"))) {
            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // fromJson returns an array
            Course[] courseArray = gson.fromJson(jsonFile, Course[].class);

            // Convert to arraylist for the data model
            for (int i = 0; i < courseArray.length; i++) {
                listOfCourses.add(courseArray[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return listOfCourses;
    }
}
