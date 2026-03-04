package Service.service;

import model.AttendanceStatus;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AttendanceService {
    void recordAttendance(String employeeId, LocalDate date, AttendanceStatus status);
    void addEmployeeAttendanceProfile(String employeeId, String name);
    void changeEmployeeName(String employeeId, String name);
    void deleteEmployeeAttendance(String employeeId);
    void addAttendanceForEmployee(String employeeId);
    void addAttendanceForEmployee(String employeeId, LocalDate date, AttendanceStatus status, double overTimeHour);
    void modifyAttendanceForEmployee(String employeeId);
    void modifyAttendanceForEmployee(String employeeId, LocalDate date, AttendanceStatus status, double overTimeHour);
    void setOverTimeOfDay(String employeeId);
    void setOverTimeOfDay(String employeeId, LocalDate date, double overTimeHour);
    void printAttendHistory(String employeeId);
    void printAttendHistory(String employeeId, int year);
    void printAttendHistory(String employeeId, int month, int year);
    void printAttendHistory(String employeeId, LocalDate day);
    int getCountAbsent(String employeeId, int month, int year);
    int getCountPresent(String employeeId, int month, int year);
    ArrayList<Integer> getDayAbsentInMonth(String employeeId, int month, int year);
    double getOverTimeHourOfMonth(String employeeId, int month, int year);
}
