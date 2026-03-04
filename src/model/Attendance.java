package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Attendance implements Serializable{

    private TreeMap<LocalDate, AttendanceStatus> attendanceManagement = new TreeMap<>(Collections.reverseOrder());
    private TreeMap<LocalDate, Double> overTimeManagement = new TreeMap<>(Collections.reverseOrder());
    private String name;

    public Attendance(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAttendance(LocalDate day, AttendanceStatus status, double overTimeHour) {
        if (attendanceManagement.containsKey(day)) {
            throw new IllegalArgumentException("This date already exists");
        }
        attendanceManagement.put(day, status);
        overTimeManagement.put(day, normalizeOverTime(status, overTimeHour));
    }

    public void modifyAttendance(LocalDate day, AttendanceStatus status, double overTimeHour) {
        if (!attendanceManagement.containsKey(day)) {
            throw new IllegalArgumentException("This date is not available");
        }
        attendanceManagement.put(day, status);
        overTimeManagement.put(day, normalizeOverTime(status, overTimeHour));
    }

    public void setOverTimeHour(LocalDate day, double overTimeHour) {
        if (!attendanceManagement.containsKey(day)) {
            throw new IllegalArgumentException("This date is not available");
        }
        AttendanceStatus status = attendanceManagement.get(day);
        if (status != AttendanceStatus.PRESENT) {
            throw new IllegalArgumentException("Only PRESENT day can have overtime");
        }
        if (overTimeHour < 0) {
            throw new IllegalArgumentException("Overtime hour must be >= 0");
        }
        overTimeManagement.put(day, overTimeHour);
    }

    private double normalizeOverTime(AttendanceStatus status, double overTimeHour) {
        if (status != AttendanceStatus.PRESENT) {
            return 0.0;
        }
        if (overTimeHour < 0) {
            throw new IllegalArgumentException("Overtime hour must be >= 0");
        }
        return overTimeHour;
    }

    private void printHistoryHeader() {
        System.out.println("===========================================================");
        System.out.println("               ATTENDANCE OF " + this.name);
        System.out.println("===========================================================\n");

        System.out.println("===========================================================");
        System.out.println("||     Date      ||     ATTENDANCE      ||    OT HOUR    ||");
        System.out.println("||  DD/MM/YYYY   ||                     ||               ||");
        System.out.println("===========================================================");
    }

    public String getLocalDate(LocalDate day) {
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return myFormat.format(day);
    }

    public void printAttendHistory() {
        this.printHistoryHeader();
        for (Map.Entry<LocalDate, AttendanceStatus> entry : attendanceManagement.entrySet()) {
            Double OTHour = overTimeManagement.get(entry.getKey());
            System.out.printf("||%15s||%21s||%15.2f||", getLocalDate(entry.getKey()), entry.getValue().getLabel(), OTHour);
            System.out.println("");
            System.out.println("===========================================================");
        }
    }

    public void printAttendHistory(int month, int year) {
        LocalDate end = LocalDate.of(year, month, 1);
        LocalDate begin = LocalDate.of(year, month, end.lengthOfMonth());
        NavigableMap<LocalDate, AttendanceStatus> subAttendance = this.attendanceManagement.subMap(begin, true, end, true);
        if (subAttendance.size() == 0) {
            System.out.println(this.getLocalDate(begin) + "-> " + getLocalDate(end) + " is not available");
            return;
        }

        this.printHistoryHeader();
        for (Map.Entry<LocalDate, AttendanceStatus> entry : subAttendance.entrySet()) {
            Double OTHour = overTimeManagement.get(entry.getKey());
            System.out.printf("||%15s||%21s||%15.2f||", getLocalDate(entry.getKey()), entry.getValue().getLabel(), OTHour);
            System.out.println("");
            System.out.println("===========================================================");
        }
    }

    public void printAttendHistory(int year) {
        LocalDate end = LocalDate.of(year, 1, 1);
        LocalDate begin = LocalDate.of(year, 12, 31);
        NavigableMap<LocalDate, AttendanceStatus> subAttendance = this.attendanceManagement.subMap(begin, true, end, true);
        if (subAttendance.size() == 0) {
            System.out.println(this.getLocalDate(begin) + "-> " + getLocalDate(end) + " is not available");
            return;
        }

        this.printHistoryHeader();
        for (Map.Entry<LocalDate, AttendanceStatus> entry : subAttendance.entrySet()) {
            Double OTHour = overTimeManagement.get(entry.getKey());
            System.out.printf("||%15s||%21s||%15.2f||", getLocalDate(entry.getKey()), entry.getValue().getLabel(), OTHour);
            System.out.println("");
            System.out.println("===========================================================");
        }
    }

    public void printAttendHistory(LocalDate target) {
        if (!this.attendanceManagement.containsKey(target)) {
            System.out.println("This date is not available! ");
            return;
        }

        String status = this.attendanceManagement.get(target).getLabel();
        Double OTHour = overTimeManagement.get(target);
        this.printHistoryHeader();
        System.out.printf("||%15s||%21s||%15.2f||", getLocalDate(target), status, OTHour);
        System.out.println("");
        System.out.println("===========================================================");
    }

    public int getCountAbsent(int month, int year) {
        int count = 0;
        for (Map.Entry<LocalDate, AttendanceStatus> entry : attendanceManagement.entrySet()) {
            if (entry.getKey().getMonthValue() == month && entry.getKey().getYear() == year && entry.getValue() == AttendanceStatus.ABSENT) {
                count++;
            }
        }
        return count;
    }

    public int getCountPresent(int month, int year) {
        int count = 0;
        for (Map.Entry<LocalDate, AttendanceStatus> entry : attendanceManagement.entrySet()) {
            if (entry.getKey().getMonthValue() == month && entry.getKey().getYear() == year && entry.getValue() == AttendanceStatus.PRESENT) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Integer> getDayAbsentInMonth(int month, int year) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (Map.Entry<LocalDate, AttendanceStatus> entry : attendanceManagement.entrySet()) {
            if (entry.getKey().getMonthValue() == month && entry.getKey().getYear() == year && entry.getValue() == AttendanceStatus.ABSENT) {
                tmp.add(entry.getKey().getDayOfMonth());
            }
        }
        return tmp;
    }

    public double getOverTimeHourOfMonth(int month, int year) {
        double sum = 0;
        for (Map.Entry<LocalDate, Double> entry : overTimeManagement.entrySet()) {
            if (entry.getKey().getMonthValue() == month && entry.getKey().getYear() == year && entry.getValue() > 0) {
                sum += entry.getValue();
            }
        }
        return sum;
    }
}
