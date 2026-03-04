package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Employee implements Serializable {
    private final String id;
    private String name;
    private String department;
    private String role;
    private LocalDate joinDay;
    private Status myStatus;
    private double basicSalary;
    private double absentDeductionRate;
    protected double overTimeRate;
    
        public Employee(String id, String name, String department, String role, LocalDate joinDay,double basicSalary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.role = role;
            this.joinDay = joinDay;
            this.basicSalary = basicSalary;
            this.myStatus = Status.ACTIVE;
        }
    
        public String getId() {
            return id;
        }
        
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getDepartment() {
            return department;
        }
    
        public void setDepartment(String department) {
            this.department = department;
        }
    
        public String getRole() {
            return role;
        }
    
        public void setRole(String role) {
            this.role = role;
        }
    
         public String getLocalDate(){
            DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return myFormat.format(joinDay);
        }// lấy ngày tháng năm theo format
    
        public void setJoinDay(LocalDate joinDay) {
            this.joinDay = joinDay;
        }
        
        public String getStatus(){
            return myStatus.getLabel();
        }
        
        public void setStatus(Status myStatus){
            this.myStatus = myStatus;
        }
        
        public abstract double getOverTimeRate();
    
        public double getBasicSalary() {
            return basicSalary;
        }
        
        public boolean isActive(){
            return myStatus == Status.ACTIVE;
        }
    
        public double getAbsentDeductionRate(){
            return absentDeductionRate;
    }

    public void setAbsentDeduction(){
        this.absentDeductionRate = 100000;
    }

    public abstract double calculateSalary(int absentCount, double overTimeHour);
    
}
