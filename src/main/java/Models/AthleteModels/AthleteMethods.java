package Models.AthleteModels;

import com.fasterxml.jackson.databind.ObjectMapper;

import Models.DOB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AthleteMethods {

    //for the jackson library, dont know why just makes things work
    public AthleteMethods() {
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String JSON_FILE = "src/main/java/Models/AthleteModels/Athletes.json";
    private static boolean writing =false;

    // Method to save current athlete to file. I think i made this method obsolete i will need to look into getting rid of it. 
    public static void saveToFile(Athlete a) {
        if (writing) {
            return; //adding this fixed an issue that would give me unlimited errors
        }
        writing = true;
        try {
            File file = new File(JSON_FILE);

            List<Athlete> athletes = readAllFromFile();
            boolean found = false;
            for (int i = 0; i < athletes.size(); i++) {
                if (athletes.get(i).getAthleteId() == a.getAthleteId()) {
                    athletes.set(i, a);
                    found = true;
                    break;
                }
            }
            if (!found) {
                athletes.add(a);
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, athletes); //writing to the json file
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        } finally {
            removeAthlete(-1);// fixes a bug that adds an invalid athlete
            writing = false; // i am no longer writing
        }
    }
    /**
     * Adds athlete to JSON file */ 
    public static void addAthlete(Athlete a){
        saveToFile(a);
    }
    /**
     * Removes an athlete from the JSON file based on their athleteId.
     * TODO: add functionality to remove by other factors such as name
     * @param athleteId The ID of the athlete to remove.
     */
    public static void removeAthlete(int athleteId) {
        try {
            File file = new File(JSON_FILE);
            List<Athlete> athletes = readAllFromFile();
            athletes.removeIf(athlete -> athlete.getAthleteId() == athleteId);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, athletes); //thank you stack overflow
        } catch (IOException e) {
            System.err.println("Error removing athlete from file: " + e.getMessage());
        }
    }

    public static void removeAthlete (String givenName,String surname)throws Exception{
        List<Athlete> matches = searchAthletes(givenName, surname);
        if (matches.size() >1){
            throw new Exception("1 or more Duplicates found with the same name, Remove by ID");
        }
        else if(matches.size()<1){
            throw new Exception("No matches found");   //This method needs to be testes as well
        }else{
            removeAthlete(matches.get(0).getAthleteId());
        }
    }
    //reads all athletes from file then returns a list of them all, needed for properly updating json file
    public static List<Athlete> readAllFromFile() {
        try {
            File file = new File(JSON_FILE);
            if (file.exists()) {
                return objectMapper.readValue(file,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Athlete.class));
            }
        } catch (IOException e) {
        }
        return new ArrayList<>();
    }

    // Method to read a specific athlete by ID
    //Havent testted this yet. no idea if the stream works 
    public static Athlete searchID(int athleteId) {
        List<Athlete> athletes = readAllFromFile();
        return athletes.stream()
                .filter(s -> s.getAthleteId() == athleteId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * searches for athletes by given name, surname, or both.
     * both a given and surname must be passed in even if user only gives 1
     * @param givenName can be null if the user only gave the surname
     * @param surname is null if the user only supplied givenName
     * @return Array of matching athletes
     */
    public static List<Athlete> searchAthletes(String givenName, String surname) {
        List<Athlete> athletes = readAllFromFile();
        
        // Filter athletes based on names given
        List<Athlete> matches = athletes.stream() //.stream and .filter is godsend
            .filter(athlete -> {
                boolean matchesGivenName = givenName == "" || athlete.getGivenName().equalsIgnoreCase(givenName); 
                boolean matchesSurname = surname == "" || athlete.getSurname().equalsIgnoreCase(surname);
                
                // If both are provided, both must match
                if (givenName != "" && surname != "") {
                    return matchesGivenName && matchesSurname;
                }
                // If only one is provided, match that one
                return matchesGivenName || matchesSurname;
            })
            .toList();
        
        // going to test this soon
        return matches;
    }

    // different methods on finding athlets by name
    public static List<Athlete> searchAthletesGivenName(String givenName){
        String surname= "";
        return searchAthletes(givenName,surname);
    }
    public static List<Athlete> searchAthletesSurname(String surname){
        String givenName= "";
        return searchAthletes(givenName,surname);
    }

    /**
     * 
     * @return all athletes
     */
    public static String printAllAthletes(){
        StringBuilder sb = new StringBuilder();
        List<Athlete> athletes = readAllFromFile();

        for(Athlete a: athletes){
            sb.append(a.printAthlete());
        }
        return sb.toString();
    }

    
}
