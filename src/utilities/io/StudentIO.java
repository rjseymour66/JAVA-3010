package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datacontainers.StudentDC;
import datamodels.Student;

import java.io.*;
import java.util.ArrayList;

public class StudentIO {

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods and should never be instantiated
     */
    private StudentIO(){
    }

    /**
     * Writes out the student data in JSON format containing all students in
     * the student data model.
     */
    public static void writeJSONFile(String fileLocation, StudentDC datacontainer) {

        try (PrintWriter jsonFile = new PrintWriter(fileLocation + "student.json")) {
            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // Convert student list to JSON format
            gson.toJson(datacontainer.getListOfStudents(), jsonFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads a JSON formatted file students and returns an array list of
     * students
     */
    public static ArrayList<Student> readJSONFile(String fileLocation) {

        ArrayList<Student> listOfStudents = new ArrayList<>();

        try (BufferedReader jsonFile = new BufferedReader(new FileReader(fileLocation + "student.json"))) {
            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // fromJson returns an array
            Student[] studentArray = gson.fromJson(jsonFile, Student[].class);

            // Convert to arrayList for the data model
            for (int i = 0; i < studentArray.length; i++) {
                listOfStudents.add(studentArray[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return listOfStudents;
    }
}
