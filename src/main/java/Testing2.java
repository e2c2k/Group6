import Database.AthletesDatabase;

public class Testing2 {
    public static void main (String[] args){
        AthletesDatabase db = new AthletesDatabase();
        try{
            db.connect();
            db.AthleteCSV();
            db.disconnect();
        }catch(Exception e){
            System.out.println("did not work");
        }
    }
}
