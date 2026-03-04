package saveLoad;

import Management.AttendanceManagement;
import Management.EmployeeManagement;
import Management.SalaryManagement;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveFile {

    private static final String EMPLOYEE_FILE = "employeeList.json";
    private static final String ATTENDANCE_FILE = "attendanceList.json";
    private static final String SALARY_FILE = "salaryList.json";

    private boolean success = true;

    public SaveFile(EmployeeManagement employeeList, AttendanceManagement attendanceList, SalaryManagement salaryList) {
        try {
            writeObject(EMPLOYEE_FILE, employeeList);
            writeObject(ATTENDANCE_FILE, attendanceList);
            writeObject(SALARY_FILE, salaryList);
        } catch (IOException ex) {
            success = false;
            System.out.println("Cannot save data: " + ex.getMessage());
        }
    }

    private void writeObject(String fileName, Object data) throws IOException {
        try (FileOutputStream os = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(data);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}
