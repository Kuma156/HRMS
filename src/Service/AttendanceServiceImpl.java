package Service;

import Management.AttendanceManagement;
import Management.EmployeeManagement;
import Service.exceptions.EmployeeNotFoundException;
import Service.exceptions.InvalidAttendanceStatusException;
import Service.exceptions.ValidationException;
import Service.service.AttendanceService;
import UserInterface.mySystem;
import inputUtils.input;

import java.time.LocalDate;
import java.util.ArrayList;
import model.AttendanceStatus;

public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceManagement attendanceManagement;
    private final EmployeeManagement employeeManagement;

    public AttendanceServiceImpl(AttendanceManagement attendanceManagement, EmployeeManagement employeeManagement) {
        this.attendanceManagement = attendanceManagement;
        this.employeeManagement = employeeManagement;
    }

    @Override
    public void recordAttendance(String employeeId, LocalDate date, AttendanceStatus status) {
        validateEmployeeId(employeeId);
        if (date == null) {
            throw new ValidationException("Attendance date is required");
        }
        if (status == null) {
            throw new InvalidAttendanceStatusException("null");
        }
        attendanceManagement.addAttendanceForEmployee(employeeId, date, status, 0.0);
    }

    @Override
    public void addEmployeeAttendanceProfile(String employeeId, String name) {
        validateEmployeeId(employeeId);
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Employee name is required");
        }
        attendanceManagement.addEmployee(employeeId, name);
    }

    @Override
    public void changeEmployeeName(String employeeId, String name) {
        validateEmployeeId(employeeId);
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Employee name is required");
        }
        attendanceManagement.changeName(employeeId, name);
    }

    @Override
    public void deleteEmployeeAttendance(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new ValidationException("Employee ID is required");
        }
        attendanceManagement.deleteEmployee(employeeId);
    }

    @Override
    public void addAttendanceForEmployee(String employeeId) {
        validateEmployeeId(employeeId);
        LocalDate date = input.inputDate();
        mySystem p = new mySystem();
        p.printAttendanceMenu();
        AttendanceStatus status = input.inputAttendance();
        double overTimeHour = p.askOverTimeHourIfPresent(status);
        validateAttendanceInput(date, status, overTimeHour);
        attendanceManagement.addAttendanceForEmployee(employeeId, date, status, overTimeHour);
    }

    @Override
    public void addAttendanceForEmployee(String employeeId, LocalDate date, AttendanceStatus status, double overTimeHour) {
        validateEmployeeId(employeeId);
        validateAttendanceInput(date, status, overTimeHour);
        attendanceManagement.addAttendanceForEmployee(employeeId, date, status, overTimeHour);
    }

    @Override
    public void modifyAttendanceForEmployee(String employeeId) {
        validateEmployeeId(employeeId);
        LocalDate date = input.inputDate();
        mySystem p = new mySystem();
        p.printAttendanceMenu();
        AttendanceStatus status = input.inputAttendance();
        double overTimeHour = p.askOverTimeHourIfPresent(status);
        validateAttendanceInput(date, status, overTimeHour);
        attendanceManagement.modifyAttendanceForEmployee(employeeId, date, status, overTimeHour);
    }

    @Override
    public void modifyAttendanceForEmployee(String employeeId, LocalDate date, AttendanceStatus status, double overTimeHour) {
        validateEmployeeId(employeeId);
        validateAttendanceInput(date, status, overTimeHour);
        attendanceManagement.modifyAttendanceForEmployee(employeeId, date, status, overTimeHour);
    }

    @Override
    public void setOverTimeOfDay(String employeeId) {
        validateEmployeeId(employeeId);
        LocalDate date = input.inputDate();
        System.out.print("Enter overtime hour: ");
        double overTimeHour = input.inputForPositiveDouble();
        setOverTimeOfDay(employeeId, date, overTimeHour);
    }

    @Override
    public void setOverTimeOfDay(String employeeId, LocalDate date, double overTimeHour) {
        validateEmployeeId(employeeId);
        if (date == null) {
            throw new ValidationException("Attendance date is required");
        }
        if (overTimeHour < 0) {
            throw new ValidationException("Overtime hour must be >= 0");
        }
        attendanceManagement.setOverTimeOfDay(employeeId, date, overTimeHour);
    }

    @Override
    public void printAttendHistory(String employeeId) {
        validateEmployeeId(employeeId);
        attendanceManagement.printAttendHistory(employeeId);
    }

    @Override
    public void printAttendHistory(String employeeId, int year) {
        validateEmployeeId(employeeId);
        validateYear(year);
        attendanceManagement.printAttendHistory(employeeId, year);
    }

    @Override
    public void printAttendHistory(String employeeId, int month, int year) {
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        attendanceManagement.printAttendHistory(employeeId, month, year);
    }

    @Override
    public void printAttendHistory(String employeeId, LocalDate day) {
        validateEmployeeId(employeeId);
        if (day == null) {
            throw new ValidationException("Attendance date is required");
        }
        attendanceManagement.printAttendHistory(employeeId, day);
    }

    @Override
    public int getCountAbsent(String employeeId, int month, int year) {
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        return attendanceManagement.getCountAbsent(employeeId, month, year);
    }

    @Override
    public int getCountPresent(String employeeId, int month, int year) {
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        return attendanceManagement.getCountPresent(employeeId, month, year);
    }

    @Override
    public ArrayList<Integer> getDayAbsentInMonth(String employeeId, int month, int year) {
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        return attendanceManagement.getDayAbsentInMonth(employeeId, month, year);
    }

    @Override
    public double getOverTimeHourOfMonth(String employeeId, int month, int year) {
        validateEmployeeId(employeeId);
        validateMonthYear(month, year);
        return attendanceManagement.getOverTimeHourOfMonth(employeeId, month, year);
    }

    private void validateEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new ValidationException("Employee ID is required");
        }
        if (employeeManagement.searchEmployeeByID(employeeId) == -1) {
            throw new EmployeeNotFoundException(employeeId);
        }
    }

    private void validateAttendanceInput(LocalDate date, AttendanceStatus status, double overTimeHour) {
        if (date == null) {
            throw new ValidationException("Attendance date is required");
        }
        if (status == null) {
            throw new InvalidAttendanceStatusException("null");
        }
        if (overTimeHour < 0) {
            throw new ValidationException("Overtime hour must be >= 0");
        }
        if (status != AttendanceStatus.PRESENT && overTimeHour > 0) {
            throw new ValidationException("Only PRESENT status can have overtime");
        }
    }

    private void validateMonthYear(int month, int year) {
        if (month < 1 || month > 12) {
            throw new ValidationException("Month must be between 1 and 12");
        }
        validateYear(year);
    }

    private void validateYear(int year) {
        if (year < 1999) {
            throw new ValidationException("Year must be >= 1999");
        }
    }
}
