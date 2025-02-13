package Models;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)


// Athlete class
public class Athlete {
    private static boolean writing = false;
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

    //for jackson i think
    public Athlete() {
    }
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

    public int getAthleteId() {return athleteId;}

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

    public void setAthleteId(int athleteId) {
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
    // Method to save current athlete to file
    public void saveToFile() {
        if (writing) {
            return; //adding this fixed an issue that would give me unlimited errors
        }
        writing = true;
        try {
            File file = new File(JSON_FILE);

            file.getParentFile().mkdirs();
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
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, athletes);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        } finally {
            writing = false; // i am no longer writing
        }
    }

    // Method to read all athletes from file
    public static List<Athlete> readAllFromFile() {
        try {
            File file = new File(JSON_FILE);
            if (file.exists()) {
                return objectMapper.readValue(file,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Athlete.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // Method to read a specific athlete by ID
    public static Athlete readFromFile(int athleteId) {
        List<Athlete> athletes = readAllFromFile();
        return athletes.stream()
                .filter(s -> s.getAthleteId() == athleteId)
                .findFirst()
                .orElse(null);
    }
}