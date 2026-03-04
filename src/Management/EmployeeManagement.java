package Management;

import model.Employee;
import model.Status;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EmployeeManagement  implements Serializable{

     private ArrayList<Employee> employeeManage = new ArrayList<>();

     public EmployeeManagement() {
    }
//CRUD  
    //Create

    public void addEmployee(Employee employee) {
        this.employeeManage.add(employee);
    }

    //Update
    public boolean updateName(String id, String newName) {
        Employee target = searchEmployee(id);
        if (target == null) {
            return false;
        }
        target.setName(newName);
        return true;
    }

    public boolean updateDepartment(String id, String department) {
        Employee target = searchEmployee(id);
        if (target == null) {
            return false;
        }
        target.setDepartment(department);
        return true;
    }

    public boolean updateRole(String id, String role) {
        Employee target = searchEmployee(id);
        if (target == null) {
            return false;
        }
        target.setRole(role);
        return true;
    }

    public boolean updateStatus(String id, Status status) {
        Employee target = searchEmployee(id);
        if (target == null) {
            return false;
        }
        target.setStatus(status);
        return true;
    }

    //Delete
    public boolean removeEmployee(String id) {
        int index = searchEmployeeByID(id);
        if (index == -1) {
            return false;
        }
       
        employeeManage.remove(employeeManage.get(index));
        return true;
    }

    //support for attendance
    public String getEmployeeName(int index){
        return employeeManage.get(index).getName();
    }
    
    
//SEARCH
    //By ID       
    public int searchEmployeeByID(String id) {
        int index = -1;

        for (int i = 0; i < employeeManage.size(); i++) {
            if (employeeManage.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }

        return index;
    }
    
     public Employee searchEmployee(String id) {
       for(Employee e : employeeManage){
           if(e.getId().equals(id)) 
               return e;
       }
         
       return null;
    }

    public List<Employee> searchEmployeeByName(String name) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : employeeManage) {
            if (e.getName().equalsIgnoreCase(name)) {
                result.add(e);
            }
        }
        return result;
    }

    //SEARCH BY DEPARTMENT
    public List<Employee> searchEmployeeByDepartment(String department) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : employeeManage) {
            if (e.getDepartment().equalsIgnoreCase(department)) {
                result.add(e);
            }
        }
        return result;
    }

    //SEARCH BY ROLE
    public List<Employee> searchEmployeeByRole(String role) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : employeeManage) {
            if (e.getRole().equalsIgnoreCase(role)) {
                result.add(e);
            }
        }
        return result;
    }
    
    //only for report
    public ArrayList<Employee> getEmployeeList(){
        return employeeManage;
    }

    public boolean isActiveEmployee(String id){
        Employee e = searchEmployee(id);
        return e!= null && e.isActive();
    }
  
}
