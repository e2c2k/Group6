

import java.util.ArrayList;
import java.util.List;

import Models.Athlete;
import Models.AthleteFileWriter;
import Models.DOB;

public class Main {
    public static void main(String[] args) {
        List<Athlete> athletes = new ArrayList<>();
      
        DOB dob1 = new DOB("july", 5, 1998);
        DOB dob2 = new DOB("August", 7, 2000);
        DOB dob3 = new DOB("June", 11, 1995);
        
        // Add athletes to the list
        athletes.add(new Athlete("Smith", "John", "Team A", dob1));
        athletes.add(new Athlete("Doe", "Jane", "Team B", dob2));
        athletes.add(new Athlete("Brown", "Charlie", "Team C", dob3));

        AthleteFileWriter.writeAthletesToFile(athletes);
    }
}
