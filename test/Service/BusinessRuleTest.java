package Service;

import Management.AttendanceManagement;
import Management.EmployeeManagement;
import Service.exceptions.DuplicateIdException;
import Service.exceptions.InvalidAttendanceStatusException;
import Service.exceptions.ValidationException;
import Service.service.AttendanceService;
import Service.service.EmployeeService;
import childrenModel.FullTimeEmployee;
import java.time.LocalDate;
import model.AttendanceStatus;
import model.Employee;

public class BusinessRuleTest {

    public static void main(String[] args) {
        run("BR1 - duplicate employee id", BusinessRuleTest::testBR1DuplicateId);
        run("BR11 - invalid attendance status null", BusinessRuleTest::testBR11NullStatus);
        run("BR11 - invalid overtime on non-present status", BusinessRuleTest::testBR11InvalidOvertime);
        run("BR11 - valid attendance accepted", BusinessRuleTest::testBR11ValidAttendance);
        System.out.println("All BR tests passed.");
    }

    private static void testBR1DuplicateId() {
        EmployeeManagement employeeManagement = new EmployeeManagement();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeManagement);

        Employee e1 = new FullTimeEmployee("NV123456", "Alice", "HR", "Staff", LocalDate.of(2025, 1, 1), 10000000);
        Employee e2 = new FullTimeEmployee("NV123456", "Bob", "IT", "Dev", LocalDate.of(2025, 2, 1), 12000000);

        employeeService.addEmployee(e1);
        assertThrows(DuplicateIdException.class, () -> employeeService.addEmployee(e2),
                "Expected DuplicateIdException for duplicate employee ID");
    }

    private static void testBR11NullStatus() {
        EmployeeManagement employeeManagement = new EmployeeManagement();
        AttendanceManagement attendanceManagement = new AttendanceManagement();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeManagement);
        AttendanceService attendanceService = new AttendanceServiceImpl(attendanceManagement, employeeManagement);

        Employee e1 = new FullTimeEmployee("HR123456", "Chris", "HR", "Staff", LocalDate.of(2025, 3, 1), 9000000);
        employeeService.addEmployee(e1);
        attendanceService.addEmployeeAttendanceProfile(e1.getId(), e1.getName());

        assertThrows(InvalidAttendanceStatusException.class,
                () -> attendanceService.recordAttendance(e1.getId(), LocalDate.of(2026, 2, 10), null),
                "Expected InvalidAttendanceStatusException for null attendance status");
    }

    private static void testBR11InvalidOvertime() {
        EmployeeManagement employeeManagement = new EmployeeManagement();
        AttendanceManagement attendanceManagement = new AttendanceManagement();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeManagement);
        AttendanceService attendanceService = new AttendanceServiceImpl(attendanceManagement, employeeManagement);

        Employee e1 = new FullTimeEmployee("MK123456", "Daisy", "MKT", "Specialist", LocalDate.of(2024, 5, 1), 11000000);
        employeeService.addEmployee(e1);
        attendanceService.addEmployeeAttendanceProfile(e1.getId(), e1.getName());

        assertThrows(ValidationException.class,
                () -> attendanceService.addAttendanceForEmployee(e1.getId(), LocalDate.of(2026, 2, 11), AttendanceStatus.ABSENT, 2.0),
                "Expected ValidationException when overtime > 0 but status is not PRESENT");
    }

    private static void testBR11ValidAttendance() {
        EmployeeManagement employeeManagement = new EmployeeManagement();
        AttendanceManagement attendanceManagement = new AttendanceManagement();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeManagement);
        AttendanceService attendanceService = new AttendanceServiceImpl(attendanceManagement, employeeManagement);

        Employee e1 = new FullTimeEmployee("AC123456", "Evan", "ACC", "Accountant", LocalDate.of(2023, 7, 1), 9500000);
        employeeService.addEmployee(e1);
        attendanceService.addEmployeeAttendanceProfile(e1.getId(), e1.getName());

        attendanceService.recordAttendance(e1.getId(), LocalDate.of(2026, 2, 12), AttendanceStatus.PRESENT);
        int present = attendanceService.getCountPresent(e1.getId(), 2, 2026);
        assertTrue(present == 1, "Expected present count = 1 after valid attendance record");
    }

    private static void run(String testName, Runnable test) {
        try {
            test.run();
            System.out.println("[PASS] " + testName);
        } catch (RuntimeException ex) {
            System.out.println("[FAIL] " + testName + " -> " + ex.getMessage());
            throw ex;
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertThrows(Class<? extends RuntimeException> expected, Runnable action, String message) {
        try {
            action.run();
        } catch (RuntimeException ex) {
            if (expected.isInstance(ex)) {
                return;
            }
            throw new AssertionError("Expected " + expected.getSimpleName() + " but got " + ex.getClass().getSimpleName(), ex);
        }
        throw new AssertionError(message);
    }
}
