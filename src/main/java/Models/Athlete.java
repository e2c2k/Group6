package Models;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**============Documentation ive been using==================
 * Link to Jackson github for documentation https://github.com/FasterXML/jackson/tree/master
 * this is how ive been doing all my JSON parsing / writing
 * some more documentation https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-core/latest/index.html
 * Object Mapper documention which is used a lot https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-databind/2.3.1/com/fasterxml/jackson/databind/ObjectMapper.html
 * stream functionality https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html // EXTREAMLY helpful
 */

/**TODO:
 * Look into saveToFile() Might be able to get rid of it // Originally for updating JSON but i found another way to do it that wasnt as buggy
 * Ill figure something out
 * GUI Implementation obv but thats in another file probably
*/

/** Needs Testing:
 * searchAthletesGivenName(), searchAthletesSurname(), SearchAthletes
 * removeAthlete(String givenName,String surname) //removing by ID should be working
 * All of rthe mutators need testing to check if they correctly update the JSON file
 * printAthletes() needs testing, i think i need to reWrite it. 
*/

// Athlete class
public class Athlete {
    private static boolean writing = false; //fixes issues
    private String surname;
    private String givenName;
    private int athleteId =-1;
    private String team;
    private DOB dob;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String JSON_FILE = "src/main/java/Models/Athletes.json";

    public Athlete(String surname, String givenName, String team, DOB dob) {
        this.surname = surname;
        this.givenName = givenName;
        this.team = team;
        this.dob = dob;
        this.athleteId = incrementAthleteId();
    }

    //for the jackson library, dont know why just makes things work
    public Athlete() {
    }
    //this method allows the auto generation of athlete ID's
    private int incrementAthleteId() {
        List<Athlete> athletes = readAllFromFile();
        int maxId = 100000;
        for (Athlete athlete: athletes)
            maxId = athlete.getAthleteId()+1;
        return maxId;
    }
    //======================Accessors===========================================
    public String getSurname() {return surname;}

    public String getGivenName(){return givenName;}

    public int getAthleteId() {return athleteId;}       //Basic Accessors 

    public String getTeam() {return team;}

    public DOB getDob() {return dob;}
    //====================Mutators======================================
    public void setSurname(String surname) {
        this.surname = surname;
        saveToFile();
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
        saveToFile();
    }

    public void setAthleteId(int athleteId) {    //Basic Mutators that i will hopefully make useful later, Not sure if they properly work or not yet
        this.athleteId = athleteId;
        saveToFile();
    }

    public void setTeam(String team) {
        this.team = team;
        saveToFile();
    }

    public void setDob(DOB dob) {
        this.dob = dob;
        saveToFile();
    }
    //============================================================
    // Method to save current athlete to file. I think i made this method obsolete i will need to look into getting rid of it. 
    private void saveToFile() {
        if (writing) {
            return; //adding this fixed an issue that would give me unlimited errors
        }
        writing = true;
        try {
            File file = new File(JSON_FILE);

            List<Athlete> athletes = readAllFromFile();
            boolean found = false;
            for (int i = 0; i < athletes.size(); i++) {
                if (athletes.get(i).getAthleteId() == this.athleteId) {
                    athletes.set(i, this);
                    found = true;
                    break;
                }
            }
            if (!found) {
                athletes.add(this);
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
        a.saveToFile();
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
        Athlete[] matches = searchAthletes(givenName, surname);
        if (matches.length >1){
            throw new Exception("1 or more Duplicates found with the same name, Remove by ID");
        }
        else if(matches.length<1){
            throw new Exception("No matches found");   //This method needs to be testes as well
        }else{
            removeAthlete(matches[0].getAthleteId());
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
    public static Athlete[] searchAthletes(String givenName, String surname) {
        List<Athlete> athletes = readAllFromFile();
        
        // Filter athletes based on names given
        List<Athlete> matches = athletes.stream() //.stream and .filter is godsend
            .filter(athlete -> {
                boolean matchesGivenName = givenName == null || athlete.getGivenName().equalsIgnoreCase(givenName); 
                boolean matchesSurname = surname == null || athlete.getSurname().equalsIgnoreCase(surname);
                
                // If both are provided, both must match
                if (givenName != null && surname != null) {
                    return matchesGivenName && matchesSurname;
                }
                // If only one is provided, match that one
                return matchesGivenName || matchesSurname;
            })
            .toList();
        
        // going to test this soon
        return matches.toArray(new Athlete[0]);
    }

    // different methods on finding athlets by name
    public static Athlete[] searchAthletesGivenName(String givenName){
        String surname= null;
        return searchAthletes(givenName,surname);
    }
    public static Athlete[] searchAthletesSurname(String surname){
        String givenName= null;
        return searchAthletes(givenName,surname);
    }

    
    
    /**
     * @param a an Athlete to print
     * @return String representation of a singular Athlete
     */
    public static String printAthlete(Athlete a){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: "+a.getGivenName() +" "+a.getSurname()).append("\tTeam: "+a.getTeam()).append("\tAthlete_ID: "+ a.getAthleteId());
        sb.append("\tDOB: "+a.getDob().getMonth()+" "+a.getDob().getDay()+ " "+ a.getDob().getYear()+"\n");
        
        return sb.toString();
    }
    /**
     * 
     * @return all athletes
     */
    public static String printAllAthletes(){
        StringBuilder sb = new StringBuilder();
        List<Athlete> athletes = readAllFromFile();

        for(Athlete a: athletes){
            sb.append(printAthlete(a));
        }
        return sb.toString();


    }
}