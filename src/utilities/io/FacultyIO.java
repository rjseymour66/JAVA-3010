package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datacontainers.FacultyDC;
import datamodels.Faculty;

import java.io.*;
import java.util.ArrayList;

public class FacultyIO {

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods and should never be instantiated.
     */
    private FacultyIO() {
    }

    /**
     * Writes out the classroom data in JSON format containing all classrooms in
     * the classroom data model.
     */
    public static void writeJSONFile(String filelocation, FacultyDC datacontainer) {

        try (PrintWriter jsonFile = new PrintWriter(filelocation + "faculty.json")) {
            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // Convert classroom list to JSON format
            gson.toJson(datacontainer.getListOfFaculty(), jsonFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Reads a JSON formatted file of faculty and returns an array list of
     * faculty
     */
    public static ArrayList<Faculty> readJSONFile(String fileLocation) {
        ArrayList<Faculty> listOfFaculty = new ArrayList<>();

        try (BufferedReader jsonFile = new BufferedReader(new FileReader(fileLocation + "faculty.json"))) {
            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // fromJson returns an array
            Faculty[] facultyArray = gson.fromJson(jsonFile, Faculty[].class);

            // Convert to arrayList for the data model
            for (int i = 0; i < facultyArray.length; i++) {
                listOfFaculty.add(facultyArray[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return listOfFaculty;
    }
}
