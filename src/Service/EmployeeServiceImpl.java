package Service;

import Management.EmployeeManagement;
import Service.exceptions.DuplicateIdException;
import Service.exceptions.ValidationException;
import Service.service.EmployeeService;
import model.Employee;
import model.Status;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeManagement employeeManagement;

    public EmployeeServiceImpl(EmployeeManagement employeeManagement) {
        this.employeeManagement = employeeManagement;
    }

    @Override
    public void addEmployee(Employee e) {
        if (e == null) throw new ValidationException("Employee is null");
        if (e.getId() == null || e.getId().trim().isEmpty())
            throw new ValidationException("ID is required");
        if (e.getName() == null || e.getName().trim().isEmpty())
            throw new ValidationException("Name is required");
        if (e.getDepartment() == null || e.getDepartment().trim().isEmpty())
            throw new ValidationException("Department is required");
        if (employeeManagement.searchEmployeeByID(e.getId()) != -1)
            throw new DuplicateIdException(e.getId());
        employeeManagement.addEmployee(e);
    }

    @Override
    public Employee getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        return employeeManagement.searchEmployee(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeManagement.getEmployeeList();
    }

    @Override
    public void printAllEmployee() {
        List<Employee> employees = employeeManagement.getEmployeeList();
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employee found.");
            return;
        }
        System.out.println("ID | Name | Department | Role | Status");
        for (Employee e : employees) {
            System.out.println(e.getId() + " | " + e.getName() + " | " + e.getDepartment()
                    + " | " + e.getRole() + " | " + e.getStatus());
        }
    }

    @Override
    public boolean updateEmployee(String id, String newName, String newDepartment, String newRole, Status newStatus) {
        int updateCount = 0;
        if (newName != null && !newName.trim().isEmpty()) updateCount++;
        if (newDepartment != null && !newDepartment.trim().isEmpty()) updateCount++;
        if (newRole != null && !newRole.trim().isEmpty()) updateCount++;
        if (newStatus != null) updateCount++;

        if (updateCount == 0) {
            throw new ValidationException("At least one update field is required");
        }
        if (updateCount > 1) {
            throw new ValidationException("Only one field can be updated per request");
        }

        if (newName != null && !newName.trim().isEmpty()) {
            return updateEmployee(id, 1, newName, null);
        }
        if (newDepartment != null && !newDepartment.trim().isEmpty()) {
            return updateEmployee(id, 2, newDepartment, null);
        }
        if (newRole != null && !newRole.trim().isEmpty()) {
            return updateEmployee(id, 3, newRole, null);
        }
        return updateEmployee(id, 4, null, newStatus);
    }

    public boolean updateEmployee(String id, int updateOption, String newValue, Status newStatus) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        if (employeeManagement.searchEmployee(id) == null) {
            throw new ValidationException("Employee not found: " + id);
        }

        switch (updateOption) {
            case 1:
                return updateName(id, newValue);
            case 2:
                return updateDepartment(id, newValue);
            case 3:
                return updateRole(id, newValue);
            case 4:
                return updateStatus(id, newStatus);
            default:
                throw new ValidationException("Invalid update option. Use 1..4");
        }
    }

    @Override
    public boolean updateName(String id, String newName) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        return employeeManagement.updateName(id, newName);
    }

    @Override
    public boolean updateDepartment(String id, String newDepartment) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        if (newDepartment == null || newDepartment.trim().isEmpty()) {
            throw new ValidationException("Department is required");
        }
        return employeeManagement.updateDepartment(id, newDepartment);
    }

    @Override
    public boolean updateRole(String id, String newRole) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        if (newRole == null || newRole.trim().isEmpty()) {
            throw new ValidationException("Role is required");
        }
        return employeeManagement.updateRole(id, newRole);
    }

    @Override
    public boolean updateStatus(String id, Status newStatus) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        if (newStatus == null) {
            throw new ValidationException("Status is required");
        }
        return employeeManagement.updateStatus(id, newStatus);
    }

    @Override
    public void removeEmployee(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        boolean removed = employeeManagement.removeEmployee(id);
        if (!removed) {
            throw new ValidationException("Employee not found: " + id);
        }
    }

    @Override
    public List<Employee> searchEmployeeByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        return employeeManagement.searchEmployeeByName(name);
    }

    @Override
    public List<Employee> searchEmployeeByRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new ValidationException("Role is required");
        }
        return employeeManagement.searchEmployeeByRole(role);
    }

    @Override
    public List<Employee> searchEmployeeByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new ValidationException("Department is required");
        }
        return employeeManagement.searchEmployeeByDepartment(department);
    }

    @Override
    public int searchEmployeeByID(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }
        return employeeManagement.searchEmployeeByID(id);
    }

    @Override
    public String getEmployeeName(int index) {
        return employeeManagement.getEmployeeName(index);
    }

    @Override
    public boolean isActiveEmployee(String id) {
        return employeeManagement.isActiveEmployee(id);
    }
}
