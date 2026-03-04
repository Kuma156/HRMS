package Service.exceptions;

public class InvalidAttendanceStatusException extends ValidationException {
    public InvalidAttendanceStatusException(String status) { super("Invalid attendance status: " + status); }
 
}
