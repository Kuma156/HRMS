package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeMap;

public class Salary implements Serializable {
    private TreeMap<Month, Double> overtimeSalary;
    private TreeMap<Month, Double> absentSalary;
    private double overTimeRate;

    public Salary(double overTimeRate) {
        this.overTimeRate = overTimeRate;
        this.overtimeSalary = new TreeMap<>();
        this.absentSalary = new TreeMap<>();
    }

    public double getOverTimeRate() {
        return overTimeRate;
    }

    public void setOverTimeSalary(int month, double hour) {
        int currentYear = LocalDate.now().getYear();
        Month m = new Month(month, currentYear);
        double amount = hour * overTimeRate;
        this.overtimeSalary.put(m, amount);
    }
    
    public void setOverTimeSalary(int month, int year, double hour) {
        Month m = new Month(month, year);
        double amount = hour * overTimeRate;
        this.overtimeSalary.put(m, amount);
    }

    public void setAbsentSalary(int month, int absentCount) {

        int currentYear = LocalDate.now().getYear();
        Month m = new Month(month, currentYear);

        this.absentSalary.put(m, (double) absentCount);
    }

    // Helper with year
    public void setAbsentSalary(int month, int year, int absentCount) {
        Month m = new Month(month, year);
        this.absentSalary.put(m, (double) absentCount);
    }

    public double getAbsentSalary(int month) {
        int currentYear = LocalDate.now().getYear();
        Month m = new Month(month, currentYear);
        return absentSalary.getOrDefault(m, 0.0);
    }

    public double calculateTotalSalary(int month, double basicSalary) {
        int currentYear = LocalDate.now().getYear();
        return calculateTotalSalary(month, currentYear, basicSalary);
    }

    public double calculateTotalSalary(int month, int year, double basicSalary) { // Helper overload
        Month m = new Month(month, year);
        getOverTimeRate(); 
        
        double otMoney = overtimeSalary.getOrDefault(m, 0.0);
        double absentCount = absentSalary.getOrDefault(m, 0.0);

        double penalty = absentCount * 100000;

        return basicSalary + otMoney - penalty;
    }

    public double getTotalSalary(int month, double basicSalary) {
        return calculateTotalSalary(month, basicSalary);
    }

    public TreeMap<Month, Double> getOverTimeSalary() {
        return overtimeSalary;
    }

    public TreeMap<Month, Double> getAbsentSalary() {
        return absentSalary;
    }
}
