package saveLoad;

import java.io.File;

/**
 *
 * @author admin
 */
public class clearData {
    public static void clear(){
        File e = new File("employeeList.json");
        File a= new File("attendanceList.json");
        File s = new File("salaryList.json");
        
        if(e.exists() || a.exists() || s.exists()){
            e.delete();
            a.delete();
            s.delete();
            System.out.println("Cleared successfully");
        }
        else System.out.println("There is no data to delete!");
        
    }
}

