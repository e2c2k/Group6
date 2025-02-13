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

    // Default constructor for Jackson
    public DOB() {}

    // Accessor methods (getters)
    public String getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

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
