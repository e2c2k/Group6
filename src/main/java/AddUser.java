import Authentication.AuthMethods;

public class AddUser {
    public static void main(String[] args) {
        
        AuthMethods.addUser("sam", "sam123");
        System.out.println("Added user: sam");
        
        AuthMethods.addUser("michael", "michael123"); 
        System.out.println("Added user: michael");

        AuthMethods.addUser("diya", "diya123"); 
        System.out.println("Added user: diya");

        AuthMethods.addUser("divyanshi", "divyanshi123"); 
        System.out.println("Added user: divyanshi");
    }
}