package Service.exceptions;

public class EmployeeNotFoundException extends ValidationException {
    public EmployeeNotFoundException(String id) {
        super("Employee not found: " + id);
    }
}
