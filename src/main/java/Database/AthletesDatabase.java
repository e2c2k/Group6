package Database;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AthletesDatabase extends DataBase{
    public AthletesDatabase(){}

    /**
     * Add an athlete to the database
     * @param surname
     * @param givenName
     * @param team
     * @param dob
     * @throws SQLException
     */
    public void addAthlete(String surname,String givenName ,String team,String dob) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Athletes (surname,givenName,team,dob) "+
                            "VALUES ("+surname+","+givenName+","+team+","+dob+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * Remove by full name
     * @param surname
     * @param givenName
     * @throws SQLException
     */
    public void removeAthlete(String surname, String givenName) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Athletes WHERE surname = " + surname + " AND givenName = " + givenName + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * remove by AthleteID
     * @param athleteID
     * @throws SQLException
     */
    public void removeAthlete(int athleteID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Athletes WHERE athleteID = " + athleteID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * 
     * @param team
     * @throws SQLException
     */
    public void removeAthlete(String team) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Athletes WHERE team = " + team + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void athleteCSV(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "select * from Athletes";
            ResultSet rs = stmt.executeQuery(sql);

            FileWriter csvWriter = new FileWriter("OutputFiles/athltes.csv");
            
            csvWriter.append("AthleteID,Surname,GivenName,Team,DOB\n");

            while (rs.next()) {
                String athleteID = rs.getString("athleteID");
                String surname = rs.getString("surname");
                String givenName = rs.getString("givenName");
                String team = rs.getString("team");
                String dob = rs.getString("dob");


                // Write row to CSV
                csvWriter.append(athleteID).append(",");
                csvWriter.append(surname).append(",");
                csvWriter.append(givenName).append(",");
                csvWriter.append(team).append(",");
                csvWriter.append(dob).append("\n");

            }

            csvWriter.flush();
            csvWriter.close();
            rs.close();
            stmt.close();


        } catch (SQLException e) {
            System.err.println("database error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error with  csv file: " + e.getMessage());
        }
    }

    
}
