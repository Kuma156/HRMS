package saveLoad;

import Management.AttendanceManagement;
import Management.EmployeeManagement;
import Management.SalaryManagement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadFile {

    private static final String EMPLOYEE_FILE = "employeeList.json";
    private static final String ATTENDANCE_FILE = "attendanceList.json";
    private static final String SALARY_FILE = "salaryList.json";

    public LoadFile() {
        ensureFileExists(EMPLOYEE_FILE);
        ensureFileExists(ATTENDANCE_FILE);
        ensureFileExists(SALARY_FILE);
    }

    private void ensureFileExists(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.out.println("Cannot create file: " + fileName);
        }
    }

    public EmployeeManagement loadEmployeeList() {
        return readObject(EMPLOYEE_FILE, EmployeeManagement.class);
    }

    public AttendanceManagement loadAttendanceList() {
        return readObject(ATTENDANCE_FILE, AttendanceManagement.class);
    }

    public SalaryManagement loadSalaryList() {
        return readObject(SALARY_FILE, SalaryManagement.class);
    }

    private <T> T readObject(String fileName, Class<T> type) {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        try (FileInputStream is = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            Object obj = ois.readObject();
            if (type.isInstance(obj)) {
                return type.cast(obj);
            }
            System.out.println("Invalid data type in " + fileName);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String detail = ex.getMessage();
            if (detail == null || detail.trim().isEmpty()) {
                detail = "invalid file format";
            }
            System.out.println("Cannot load " + fileName + ": " + detail);
            return null;
        }
    }
}
