package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

public class Report implements Serializable {
    private ArrayList<ReportFormForAttendance> reportAttendance;
    private ArrayList<ReportFormForSalary> reportSalary;
    private ArrayList<Employee> employeeManage;
    private TreeMap<String, Attendance> employeeAttendance; 
    private TreeMap<String, Salary> employeeSalary;

    public Report(ArrayList<Employee> employeeManage, TreeMap<String, Attendance> employeeAttendance) {
        this.employeeManage = employeeManage;
        this.employeeAttendance = employeeAttendance;
        this.reportAttendance = new ArrayList<>();
    }

    public Report(ArrayList<Employee> employeeManage, TreeMap<String, Salary> employeeSalary, boolean forSalary) { 
        this.employeeManage = employeeManage;
        this.employeeSalary = employeeSalary;
        this.reportSalary = new ArrayList<>();
    }

    public void viewTopHighestSalary(int month, int year) {
        reportSalary.clear();
        for (Employee e : employeeManage){
            String id = e.getId();
            if (employeeSalary.containsKey(id)){
               Salary salary = employeeSalary.get(id);
               double total = salary.calculateTotalSalary(month, year, e.getBasicSalary());
               reportSalary.add(new ReportFormForSalary(id, e.getName(), total));
            }
        }
        Collections.sort(reportSalary, new Comparator<ReportFormForSalary>() {
            @Override
            public int compare(ReportFormForSalary o1, ReportFormForSalary o2){
                return Double.compare(o2.getTotalSalary(), o1.getTotalSalary());
            }
        });
        System.out.println("----- TOP HIGHEST SALARY EMPLOYEE -----");
        for (int i = 0; i < Math.min(reportSalary.size(), 5); i++) {
            ReportFormForSalary top = reportSalary.get(i);
            System.out.println(top.getName() + " - Salary: " + top.getTotalSalary());
        }
    }

    public void viewLowestAttendance(int month, int year) {
        reportAttendance.clear();
        for (Employee e : employeeManage) {
            String id = e.getId();
            if (employeeAttendance.containsKey(id)) {
                Attendance a = employeeAttendance.get(id);
                ArrayList<Integer> absentDaysInMonth = a.getDayAbsentInMonth(month, year);
                reportAttendance.add(new ReportFormForAttendance(id, e.getName(), absentDaysInMonth));
            }
        }

        // Sort by absent days count descending (Most absent = Lowest Attendance)
        Collections.sort(reportAttendance, new Comparator<ReportFormForAttendance>() {
            @Override
            public int compare(ReportFormForAttendance o1, ReportFormForAttendance o2) {
                return Integer.compare(o2.getTotalAbsentDays(), o1.getTotalAbsentDays()); 
            }
        });
        System.out.println("----- LOWEST ATTENDANCE EMPLOYEE -----");
        for (int i = 0; i < Math.min(reportAttendance.size(), 5); i++) {
            ReportFormForAttendance low = reportAttendance.get(i);
            System.out.println(low.getName() + " - Absent: " + low.getAbsentDayInMonth());
        }
    }

}
