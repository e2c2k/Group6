import Models.Athlete;
import Models.DOB;
import java.util.*;

/**
 * i want to completely redo this. Split the different functionality into different files to make it easier to understand
 * ex. if someone wanted to remove an athlete the code would jump to another file where all the code related to 
 * different ways of removing would be or different ways of searching for someone or adding ect.
 * 
 * This file may also be replaced on whatever GUI we decide to go with.
 */
public class Testing {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add a new athlete");
            System.out.println("2. Remove an athlete");
            System.out.println("3. Find an athlete by ID");
            System.out.println("4. Print All Athletes");
            System.out.println("5.Search and print Athlete or Athletes");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    
                    System.out.print("\nSurname: ");
                    String surname = scanner.nextLine();
                    System.out.print("\nGiven Name: ");
                    String givenname = scanner.nextLine();
                    System.out.print("\nTeam name: ");
                    String team = scanner.nextLine();    // all of this is gathering new info to add a new AThlete
                    System.out.print("\nMonth: ");
                    String month = scanner.nextLine();
                    System.out.print("\nDay: ");
                    int day = scanner.nextInt();
                    System.out.print("\nYear: ");
                    int year = scanner.nextInt();
                    Athlete.addAthlete(new Athlete(surname, givenname, team, new DOB(month, day, year)));
                    System.out.println("Athlete added successfully.");
                    break;
                case 2:
                    // Remove an athlete using ID
                    System.out.print("Enter the ID of the athlete to remove: ");
                    int removeId = scanner.nextInt();
                    Athlete.removeAthlete(removeId);
                    System.out.println("Athlete with ID " + removeId + " has been removed.");
                    break;
                case 3:
                    // Find an athlete by ID
                    System.out.print("Enter the ID:  ");
                    int findId = scanner.nextInt();
                    Athlete foundAthlete = Athlete.searchID(findId);
                    if (foundAthlete != null) {
                        System.out.println("Found athlete: " + foundAthlete.getGivenName() + " " + foundAthlete.getSurname());
                    } else {
                        System.out.println("No athlete found with ID " + findId);
                    }
                    break;
                case 4:
                    System.out.println(Athlete.printAllAthletes());
                    break;
                case 5:
                    int select =0;
                    while(select !=1 && select !=2){
                        System.out.println("enter 1 for givenName  ");
                        System.out.println("Enter 2 for Surname  ");
                        System.out.print("Enter Choice: ");
                        select =scanner.nextInt();
                    }
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    switch(select){
                        case 1:
                           Athlete[] a = Athlete.searchAthletesGivenName(name);
                           Athlete.printAthlete(a[0]);
                        case 2:
                           Athlete[] b = Athlete.searchAthletesSurname(name);
                           Athlete.printAthlete(b[0]);
                    }

                    break;
                    
                case 6:
                    // Exit
                    System.out.println("Exiting the program");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);
        scanner.close();
    }
}
    
 
