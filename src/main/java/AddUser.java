import Authentication.AuthMethods;

public class AddUser {
    public static void main(String[] args) {
        String username = "sam"; 
        String password = "sam123"; 
        AuthMethods.addUser(username, password);
        System.out.println("User added successfully!");
    }
}