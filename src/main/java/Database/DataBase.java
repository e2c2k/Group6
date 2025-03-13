package Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DataBase {
    
    protected Connection conn;
    protected String url = "jdbc:sqlite:Track.db";
    // Connect to the database
    public void connect() throws SQLException {
        try{
            conn = DriverManager.getConnection(url);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Disconnect from the database
    public void disconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Going to write this on the weekend or thursday havent decided yet
     * @return string representation of the table for GUI
     */
    public String displayTable(String tableName) throws SQLException{
        StringBuilder table = new StringBuilder();

        List<String> columnNames = new ArrayList<>();
        List<Integer> columnWidths = new ArrayList<>();
        List<List<String>> rows = new ArrayList<>();
        
        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // get column names and and set the column width so it looks nicer
            ResultSetMetaData metaData = rs.getMetaData(); // used to determine the table structure like column names etc
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String colName = metaData.getColumnName(i);
                columnNames.add(colName);
                columnWidths.add(colName.length()); 
            }
            
            // get rows and find max widths
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i) != null ? rs.getString(i) : "NULL";
                    row.add(value);
                    // Update column width if this value is wider
                    columnWidths.set(i - 1, Math.max(columnWidths.get(i - 1), value.length()));
                }
                rows.add(row);
            }
            
            // this is where formatting the string starts
            // header
            for (int i = 0; i < columnCount; i++) {
                table.append(moveRight(columnNames.get(i), columnWidths.get(i)));
                if (i < columnCount - 1) table.append(" | ");
            }
            table.append("\n");
            
            // just to seperate the rows properly
            for (int i = 0; i < columnCount; i++) {
                table.append(repeat('-', columnWidths.get(i)));
                if (i < columnCount - 1) table.append("-+-");
            }
            table.append("\n");
            
            // rows
            for (List<String> row : rows) {
                for (int i = 0; i < columnCount; i++) {
                    table.append(moveRight(row.get(i), columnWidths.get(i)));
                    if (i < columnCount - 1) table.append(" | ");
                }
                table.append("\n");
            }
        }
        
        return table.toString();
    }
    //used to make it look nice and move a string to the right soi it fits properly
    private static String moveRight(String s, int width) {
        return String.format("%-" + width + "s", s);
    }
    
    // used to add lines to divy up the table and make it look nice
    private static String repeat(char ch, int times) {
        return String.valueOf(ch).repeat(times);
    }
    
}

