
import Database.AthletesDatabase;
import Database.EventsDatabase;
import Database.HeatsDataBase;
import Database.MeetsDatabase;
import Database.ResultsDataBase;

public class Testing2 {
    public static void main (String[] args){
        AthletesDatabase db = new AthletesDatabase();
        MeetsDatabase db2 = new MeetsDatabase();
        ResultsDataBase db3 = new ResultsDataBase();
        
        try{
            db.connect();
            db.athleteCSV();
            db.disconnect();
            db2.connect();
            db2.meetCSV();
            db2.disconnect();
            db3.connect();
            db3.resultCSV();
            db3.disconnect();
        }catch(Exception e){
            System.out.println("did not work");
        }
    }
}
