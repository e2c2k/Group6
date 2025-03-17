
import Database.AthletesDatabase;
import Database.DataBase;
import Database.EntriesDatabase;
import Database.EventsDatabase;
import Database.HeatsDataBase;
import Database.MeetsDatabase;
import Database.ResultsDataBase;

public class Testing2 {
    public static void main (String[] args){
        AthletesDatabase ATH = new AthletesDatabase();
        MeetsDatabase MEET = new MeetsDatabase();
        ResultsDataBase RESULT = new ResultsDataBase();
        HeatsDataBase HEAT = new HeatsDataBase();
        EventsDatabase EVENT = new EventsDatabase();
        EntriesDatabase ENTRY = new EntriesDatabase();
        
        try{
            //code to test
            HEAT.connect();
            HEAT.heatsCSV();
            HEAT.disconnect();
        }catch(Exception e){
            System.out.println("did not work");
        }
    }
}
