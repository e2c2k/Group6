package Models.AthleteModels;


import Models.DOB;


import java.util.List;
/**============Documentation ive been using==================
 * Link to Jackson github for documentation https://github.com/FasterXML/jackson/tree/master
 * this is how ive been doing all my JSON parsing / writing
 * some more documentation https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-core/latest/index.html
 * Object Mapper documention which is used a lot https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-databind/2.3.1/com/fasterxml/jackson/databind/ObjectMapper.html
 * stream functionality https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html // EXTREAMLY helpful
 */

/**TODO:
 * 
 * GUI Implementation obv but thats in another file probably
*/

/** Needs Testing:
 * 
 * 
 * All of the mutators need testing to check if they correctly update the JSON file
 * printAthletes() needs testing, i want  to make the output look better
*/

// Athlete class
public class Athlete {
    private String surname;
    private String givenName;
    private int athleteId =-1;
    private String team;
    private DOB dob;
    

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
        List<Athlete> athletes = AthleteMethods.readAllFromFile();
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
        AthleteMethods.saveToFile(this);
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
        AthleteMethods.saveToFile(this);
    }

    public void setAthleteId(int athleteId) {    //Basic Mutators that i will hopefully make useful later, Not sure if they properly work or not yet
        this.athleteId = athleteId;
        AthleteMethods.saveToFile(this);
    }

    public void setTeam(String team) {
        this.team = team;
        AthleteMethods.saveToFile(this);
    }

    public void setDob(DOB dob) {
        this.dob = dob;
        AthleteMethods.saveToFile(this);
    }
    
    /**
     * 
     * @return String representation of a singular Athlete
     */
    public String printAthlete(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: "+givenName +" "+surname).append("\tTeam: "+team).append("\tAthlete_ID: "+ athleteId);
        sb.append("\tDOB: "+this.getDob().getMonth()+" "+this.getDob().getDay()+ " "+ this.getDob().getYear()+"\n");
        
        return sb.toString();
    }
    


    
}