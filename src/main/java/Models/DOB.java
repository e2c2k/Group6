package Models;

public class DOB {
    private String month;
    private int day;
    private int year;

    // Constructor
    public DOB(String month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    //constructor for Jackson
    public DOB() {}

    // Accessor 
    public String getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }
    // Mutators
    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
