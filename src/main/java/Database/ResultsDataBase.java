package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultsDataBase extends DataBase{

    public ResultsDataBase(){}

    public void addResult(int entryID,double actualTime,int place)throws SQLException{
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Results (entryID,actualTime,place) "+
                            "VALUES ("+entryID+","+actualTime+","+place+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void resultCSV(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "select * from results";
            ResultSet rs = stmt.executeQuery(sql);

            FileWriter csvWriter = new FileWriter("OutputFiles/results.csv");
            
            csvWriter.append("ResultID,EntryID,ActualTime,Place\n");

            while (rs.next()) {
                String resultID = rs.getString("resultID");
                String entryID = rs.getString("entryID");
                String actualTime = rs.getString("actualTime");
                String place = rs.getString("Place");


                // Write row to CSV
                csvWriter.append(resultID).append(",");
                csvWriter.append(entryID).append(",");
                csvWriter.append(actualTime).append(",");
                csvWriter.append(place).append(",");
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
