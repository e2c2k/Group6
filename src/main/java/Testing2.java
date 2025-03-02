import Database.AthletesDatabase;
import Database.EntriesDatabase;

public class Testing2 {
    public static void main (String[] args){
        AthletesDatabase db = new AthletesDatabase();
        EntriesDatabase db2 = new EntriesDatabase();
        try{
            db.connect();
            db.athleteCSV();
            db.disconnect();
        }catch(Exception e){
            System.out.println("did not work");
        }
    }
}
