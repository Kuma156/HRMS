package model;

import java.io.Serializable;

public class Month implements Comparable<Month>, Serializable {
    private static final long serialVersionUID = 1L;

    int month;
    int year;

    public Month(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Month other) {
        if (this.year != other.year)
            return Integer.compare(this.year, other.year);
        return Integer.compare(this.month, other.month);
    }
    
    @Override
    public String toString() {
        return String.format("%02d/%d", month, year);
    }

    
}
