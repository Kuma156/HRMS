package Management;

import java.io.Serializable;
import java.util.TreeMap;
import model.Salary;

/**
 *
 * @author The Miracle Invoker
 */
public class SalaryManagement implements Serializable {
    private TreeMap<String, Salary> employeeSalary;

    public SalaryManagement(TreeMap<String, Salary> employeeSalary) {
        this.employeeSalary = new TreeMap<>(employeeSalary);
    }
    
    public void addEmployeeSalary(String id, Salary salary){
        this.employeeSalary.put(id, salary);
    }

    public void deleteEmployeeSalary(String id){
        if(employeeSalary.containsKey(id))
            employeeSalary.remove(id);
    }
    
    public boolean modifierSalary(String id, Salary salary){
        if(employeeSalary.containsKey(id)) {
            employeeSalary.put(id, salary);
            return true;
        }
        return false;
    }
    
    public TreeMap<String, Salary> getAllEmployeeSalary(){
        return employeeSalary;
    }
}
