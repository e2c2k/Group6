
import Database.EventsDatabase;

public class Testing2 {
    public static void main (String[] args){
        EventsDatabase db = new EventsDatabase();
        
        try{
            db.connect();
            db.eventsCSV();
            db.disconnect();
        }catch(Exception e){
            System.out.println("did not work");
        }
    }
}
