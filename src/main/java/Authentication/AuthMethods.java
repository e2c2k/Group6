package Authentication;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AuthMethods {

  private static final String USERS_FILE = "src/main/java/Authentication/users.json";

  public static void addUser(String username, String password){
    try{
      String hashedPassword = AuthUtils.hashPassword(password);

      String content = new String(Files.readAllBytes(Paths.get(USERS_FILE)));
      JSONObject json = new JSONObject(content);
      JSONArray users = json.getJSONArray("users");

      //Adding new user to the JSON array
      JSONObject newUser = new JSONObject();
      newUser.put("username", username);
      newUser.put("password", hashedPassword);
      users.put(newUser);

      // Write the updated JSON back to file
      Files.write(Paths.get(USERS_FILE), json.toString(4).getBytes());
      
    } catch(Exception e) {
      throw new RuntimeException("Error adding user", e);
    }
  }

  public static boolean verifyUser(String username, String password){
    try{
      String content = new String(Files.readAllBytes(Paths.get(USERS_FILE)));
      JSONObject json = new JSONObject(content);
      JSONArray users = json.getJSONArray("users");

      for(int i = 0; i < users.length(); i++){
        JSONObject user = users.getJSONObject(i);
        if(user.getString("username").equals(username)){
          String hashedPassword = user.getString("password");
          return AuthUtils.verifyPassword(password, hashedPassword);
        }
      }
      return false; //User not found
    } 
    catch(Exception e){
      throw new RuntimeException("Error verifying user", e);
    }
  }

  public static void main(String[] args) {
    // Add test users
    try {
      addUser("john", "password123");
      addUser("mary", "password456");
      System.out.println("Users added successfully!");
    } catch (Exception e) {
      System.err.println("Error adding users: " + e.getMessage());
    }
  }
}
