package Management;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;
import model.Attendance;
import model.AttendanceStatus;

public class AttendanceManagement implements Serializable {
    TreeMap<String, Attendance> attendanceManagement = new TreeMap<>();

    public void addEmployee(String id, String name) {
        Attendance tmp = new Attendance(name);
        attendanceManagement.put(id, tmp);
    }

    public void deleteEmployee(String id) {
        if (attendanceManagement.containsKey(id)) {
            attendanceManagement.remove(id);
        }
    }

    public void changeName(String id, String name) {
        if (attendanceManagement.containsKey(id)) {
            attendanceManagement.get(id).setName(name);
        }
    }

    public void addAttendanceForEmployee(String id, LocalDate day, AttendanceStatus attend, double overTimeHour) {
        Attendance attendance = attendanceManagement.get(id);
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance profile not found: " + id);
        }
        attendance.addAttendance(day, attend, overTimeHour);
    }

    public void modifyAttendanceForEmployee(String id, LocalDate day, AttendanceStatus status, double overTimeHour) {
        Attendance attendance = attendanceManagement.get(id);
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance profile not found: " + id);
        }
        attendance.modifyAttendance(day, status, overTimeHour);
    }

    public void setOverTimeOfDay(String id, LocalDate day, double overTimeHour) {
        Attendance attendance = attendanceManagement.get(id);
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance profile not found: " + id);
        }
        attendance.setOverTimeHour(day, overTimeHour);
    }

    public void printAttendHistory(String id) {
        this.attendanceManagement.get(id).printAttendHistory();
    }

    public void printAttendHistory(String id, int year) {
        this.attendanceManagement.get(id).printAttendHistory(year);
    }

    public void printAttendHistory(String id, int month, int year) {
        this.attendanceManagement.get(id).printAttendHistory(month, year);
    }

    public void printAttendHistory(String id, LocalDate day) {
        this.attendanceManagement.get(id).printAttendHistory(day);
    }

    public int getCountAbsent(String id, int month, int year) {
        return this.attendanceManagement.get(id).getCountAbsent(month, year);
    }

    public int getCountPresent(String id, int month, int year) {
        return this.attendanceManagement.get(id).getCountPresent(month, year);
    }

    public ArrayList<Integer> getDayAbsentInMonth(String id, int month, int year) {
        return this.attendanceManagement.get(id).getDayAbsentInMonth(month, year);
    }

    public double getOverTimeHourOfMonth(String id, int month, int year) {
        return this.attendanceManagement.get(id).getOverTimeHourOfMonth(month, year);
    }
}
