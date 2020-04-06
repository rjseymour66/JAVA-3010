/*
 *  This Class contains methods to write out the classroom objects in several different formats
 *  and read in the data in the same formats.
 */
package utilities.io;

import datamodels.Classroom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import datacontainers.ClassroomDC;


public class ClassroomIO {

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods and should never be instantiated
     */
    private ClassroomIO() {
    }

    /**
     * Writes out the classroom data in JSON format containing all classrooms in
     * the classroom data model
     *
     */
    public static void writeJSONFile(String fileLocation, ClassroomDC datacontainer) {

        try (PrintWriter jsonFile = new PrintWriter(fileLocation + "classroom.json")) {
            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // Convert classroom list to JSON format
            gson.toJson(datacontainer.getListOfClassrooms(), jsonFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());        }
    }

    /**
     * Reads a JSON formatted file of classrooms and returns an array list of
     * classrooms.
     *
     */
    public static ArrayList<Classroom> readJSONFile(String fileLocation) {

        ArrayList<Classroom> listOfClassrooms = new ArrayList<>();

        try (BufferedReader jsonFile = new BufferedReader(new FileReader(fileLocation + "classroom.json"))) {
            Gson gson = new GsonBuilder().create();

            // fromJson returns an array
            Classroom[] classroomArray = gson.fromJson(jsonFile, Classroom[].class);

            // Convert to arraylist for the data model
            for (int i = 0; i < classroomArray.length; i++) {
                listOfClassrooms.add(classroomArray[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return listOfClassrooms;

    }
}
