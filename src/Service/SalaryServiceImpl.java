package Service;

import Management.AttendanceManagement;
import Management.EmployeeManagement;
import Management.SalaryManagement;
import Service.exceptions.ValidationException;
import Service.service.SalaryService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import model.Employee;
import model.Salary;

public class SalaryServiceImpl implements SalaryService {
    private final SalaryManagement salaryManagement;
    private final EmployeeManagement employeeManagement;
    private final AttendanceManagement attendanceManagement;

    public SalaryServiceImpl(SalaryManagement salaryManagement) {
        this(salaryManagement, null, null);
    }

    public SalaryServiceImpl(SalaryManagement salaryManagement, EmployeeManagement employeeManagement,
            AttendanceManagement attendanceManagement) {
        this.salaryManagement = salaryManagement;
        this.employeeManagement = employeeManagement;
        this.attendanceManagement = attendanceManagement;
    }

    @Override
    public void addOrUpdateSalary(String employeeId, Salary salary) {
        validateEmployeeId(employeeId);
        if (salary == null) {
            throw new ValidationException("Salary object is required");
        }
        salaryManagement.addEmployeeSalary(employeeId, salary);
    }

    @Override
    public Salary getSalary(String employeeId) {
        validateEmployeeId(employeeId);
        return salaryManagement.getAllEmployeeSalary().get(employeeId);
    }

    @Override
    public Map<String, Salary> getAllSalary() {
        return salaryManagement.getAllEmployeeSalary();
    }

    @Override
    public void printPersonalSalary(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        printPersonalSalary(employee);
        Salary salary = salaryManagement.getAllEmployeeSalary().get(employeeId);
        if (salary != null) {
            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            double total = salary.calculateTotalSalary(month, year, employee.getBasicSalary());
            System.out.println("Total salary " + month + "/" + year + ": " + total);
        }
    }

    @Override
    public void printPersonalSalary(Employee employee) {
        if (employee == null) {
            throw new ValidationException("Employee is required");
        }
        String id = employee.getId();
        Salary salary = salaryManagement.getAllEmployeeSalary().get(id);
        if (salary == null) {
            System.out.println("No salary record for this employee.");
            return;
        }
        System.out.println("Salary Info for: " + employee.getName());
        System.out.println("Basic Salary: " + employee.getBasicSalary());
        System.out.println("Overtime Salary Records: " + salary.getOverTimeSalary());
        System.out.println("Absent Penalty Records: " + salary.getAbsentSalary());
    }

    @Override
    public void addSalaryFromAttendance(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int absentCount = attendanceManagement.getCountAbsent(employeeId, month, year);
        double overTimeHours = attendanceManagement.getOverTimeHourOfMonth(employeeId, month, year);
        addSalaryFromAttendance(employee, employeeId, month, year, absentCount, overTimeHours);
    }

    @Override
    public void addSalaryFromAttendance(Employee employee, String employeeId, int month, int year, int absentCount, double overTimeHours) {
        validateEmployee(employee);
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        validateNonNegative(absentCount, "Absent count");
        validateNonNegative(overTimeHours, "Overtime hours");
        Salary salary = new Salary(employee.getOverTimeRate());
        salary.setAbsentSalary(month, year, absentCount);
        salary.setOverTimeSalary(month, year, overTimeHours);
        salaryManagement.addEmployeeSalary(employeeId, salary);
    }

    @Override
    public void updateSalaryFromAttendance(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int absentCount = attendanceManagement.getCountAbsent(employeeId, month, year);
        double overTimeHours = attendanceManagement.getOverTimeHourOfMonth(employeeId, month, year);
        updateSalaryFromAttendance(employee, employeeId, month, year, absentCount, overTimeHours);
    }

    @Override
    public void updateSalaryFromAttendance(Employee employee, String employeeId, int month, int year, int absentCount, double overTimeHours) {
        validateEmployee(employee);
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        validateNonNegative(absentCount, "Absent count");
        validateNonNegative(overTimeHours, "Overtime hours");
        Salary salary = new Salary(employee.getOverTimeRate());
        salary.setAbsentSalary(month, year, absentCount);
        salary.setOverTimeSalary(month, year, overTimeHours);
        boolean updated = salaryManagement.modifierSalary(employeeId, salary);
        if (!updated) {
            throw new ValidationException("No salary record for employee: " + employeeId);
        }
    }

    @Override
    public double calculateTotalSalary(String employeeId, int month, int year, double basicSalary) {
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        validateNonNegative(basicSalary, "Basic salary");
        Salary salary = salaryManagement.getAllEmployeeSalary().get(employeeId);
        if (salary == null) {
            throw new ValidationException("No salary record for employee: " + employeeId);
        }
        return salary.calculateTotalSalary(month, year, basicSalary);
    }

    @Override
    public void printAllSalary() {
        ensureContextForAutoBuild();
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        printAllSalary(employeeManagement.getEmployeeList(), month, year);
    }

    @Override
    public void printAllSalary(List<Employee> employees, int month, int year) {
        if (employees == null) {
            throw new ValidationException("Employee list is required");
        }
        validateMonthYear(month, year);
        for (Employee e : employees) {
            Salary salary = salaryManagement.getAllEmployeeSalary().get(e.getId());
            if (salary != null) {
                double total = salary.calculateTotalSalary(month, year, e.getBasicSalary());
                System.out.println(e.getId() + " | " + e.getName() + " | " + total);
            }
        }
    }

    private Employee getEmployeeById(String employeeId) {
        validateEmployeeId(employeeId);
        ensureContextForAutoBuild();
        Employee employee = employeeManagement.searchEmployee(employeeId);
        if (employee == null) {
            throw new ValidationException("Employee not found: " + employeeId);
        }
        return employee;
    }

    private void ensureContextForAutoBuild() {
        if (employeeManagement == null || attendanceManagement == null) {
            throw new ValidationException("Salary service is missing employee/attendance context");
        }
    }

    private void validateEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new ValidationException("Employee ID is required");
        }
    }

    private void validateEmployee(Employee employee) {
        if (employee == null) {
            throw new ValidationException("Employee is required");
        }
    }

    private void validateMonthYear(int month, int year) {
        if (month < 1 || month > 12) {
            throw new ValidationException("Month must be between 1 and 12");
        }
        if (year < 1999) {
            throw new ValidationException("Year must be >= 1999");
        }
    }

    private void validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new ValidationException(fieldName + " must be >= 0");
        }
    }
}
