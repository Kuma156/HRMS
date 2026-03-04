package inputUtils;

import model.Employee;
import java.util.ArrayList;


public class validator {
    public static boolean checkValidDate(int day, int month, int year) {
        if (year < 1 || month < 1 || month > 12 || day < 1) {
            return false;
        }

        int maxDay;
        switch (month) {
            case 4: case 6: case 9: case 11:
                maxDay = 30;
                break;
            case 2:
                // Kiểm tra năm nhuận: Chia hết cho 400 HOẶC (chia hết cho 4 và không chia hết cho 100)
                if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
                break;
            default: // Các tháng 1, 3, 5, 7, 8, 10, 12
                maxDay = 31;
                break;
        }

        return day <= maxDay;
        //kiem tra month year roi gio kiem tra day
        //neu day vuot qua so ngay thi no tra ve false
    }
    
    public static Boolean isExistID(String id , ArrayList<Employee> tmp){
        for(Employee e: tmp){
            if(e.getId().equals(id)) return true;
        }
        return false;
    }

    public static boolean stringIsDigit(String name) {
       for(int i=0;i<name.length();i++){
           if(Character.isDigit(name.charAt(i)))
               return true;
       }
       return false;
       
    }
    
}