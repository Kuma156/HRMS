package Service.service;

import java.util.List;

import model.Employee;
import model.Status;

public interface EmployeeService {
    void addEmployee(Employee e);
    Employee getById(String id);
    List<Employee> getAll();
    void printAllEmployee();
    //boolean updateEmployee(String id);
    boolean updateName(String id, String newName);
    boolean updateDepartment(String id, String newDepartment);
    boolean updateRole(String id, String newRole);
    boolean updateStatus(String id, Status newStatus);
    void removeEmployee(String id);
    List<Employee> searchEmployeeByName(String name);
    List<Employee> searchEmployeeByRole(String role);
    List<Employee> searchEmployeeByDepartment(String department);
    int searchEmployeeByID(String id);
    String getEmployeeName(int index);
    boolean isActiveEmployee(String id);
    boolean updateEmployee(String id, String newName, String newDepartment, String newRole, Status newStatus);
}
