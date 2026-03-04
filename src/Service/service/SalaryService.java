package Service.service;

import model.Employee;
import model.Salary;
import java.util.Map;
import java.util.List;

public interface SalaryService {
    void addOrUpdateSalary(String employeeId, Salary salary);
    Salary getSalary(String employeeId);
    Map<String, Salary> getAllSalary();
    void printPersonalSalary(String employeeId);
    void printPersonalSalary(Employee employee);
    void addSalaryFromAttendance(String employeeId);
    void addSalaryFromAttendance(Employee employee, String employeeId, int month, int year, int absentCount, double overTimeHours);
    void updateSalaryFromAttendance(String employeeId);
    void updateSalaryFromAttendance(Employee employee, String employeeId, int month, int year, int absentCount, double overTimeHours);
    double calculateTotalSalary(String employeeId, int month, int year, double basicSalary);
    void printAllSalary();
    void printAllSalary(List<Employee> employees, int month, int year);
}
