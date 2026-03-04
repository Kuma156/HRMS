package Service.exceptions;

public class DuplicateIdException extends ValidationException {
    public DuplicateIdException(String id) { super("Duplicate employee id: " + id); }
}