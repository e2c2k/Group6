import Models.Athlete;
import Models.DOB;
import java.util.*;
public class Testing {
    public static void main (String[] args){

        //Create a new athlete
        Athlete athlete = new Athlete("Johnstone", "Logan", "Truro Feilds", new DOB("march", 18, 2004));
        athlete.saveToFile();
    }
}