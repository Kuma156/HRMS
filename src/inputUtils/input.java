package inputUtils;

import java.time.LocalDate;
import java.time.Year;
import java.util.InputMismatchException;
import model.Status;
import java.util.Scanner;
import model.AttendanceStatus;


public class input {
    private static Scanner sc = new Scanner(System.in);

    public static int inputChoice(int min, int max) {
        
        while (true) {
            try {
                int choice = Integer.parseInt(sc.nextLine());

                if (choice < min || choice > max) {
                    System.out.println( "Choice must be from " + min + " to " + max);
                }
                else return choice;
            } catch (NumberFormatException e) {
                System.out.print("Please enter valid number: ");
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }

        }
    }
    
     public static String inputId() {
          System.out.println("ID FORMAT: XX123456");
          System.out.println("Role code + 6 digits ");
        while (true) {
                System.out.print("===Enter id :");
                String id = sc.nextLine().trim().toUpperCase();

                if (!id.matches("[A-Z]{2}\\d{6}")) {
                    System.out.println("Wrong format!");
                }
                else return id;
            }
        }
    
     
      public static String inputName() {
         NameProccesser np= new NameProccesser();
        while (true) {
                System.out.print("===Enter name: ");
                String name = np.setTrimName(sc.nextLine());
                       

                if (name.length() <2 || name.length()>35) {
                    System.out.println("Name length must be 2-35 character");
                }
                else if(validator.stringIsDigit(name)){
                    System.out.println("Name can not contain digit!");
                }
             
                else return name;
        }
     }
    
     public static String inputRole() {
         NameProccesser np= new NameProccesser();
        while (true) {
                System.out.print("===Enter role: ");
                String role=  np.setTrimName(sc.nextLine());
           
                 if (role.length() <2 || role.length()>25) {
                    System.out.println("Role length must be 2-25 character!");
                }
                 else if(Character.isDigit(role.charAt(0))){
                     System.out.println("The first character can not be a number!");
                 }
                 else return role;
        }
    }
     
    public static String inputDepartment() {
         NameProccesser np= new NameProccesser();
        while (true) {
                System.out.print("===Enter department: ");
                String department= np.setTrimName(sc.nextLine());
                  if (department.length() <2 || department.length()>20) {
                    System.out.println("Department length must be 2-20 character");
                }
                  else if(Character.isDigit(department.charAt(0))){
                     System.out.println("The first character can not be a number!");
                 }
                  else return department;
        }
    }

    public static LocalDate inputDate(){
        int day = 0, month = 0, year = 0;
        boolean isValidDate = false;
        LocalDate joinDay;
        // Vòng lặp yêu cầu nhập cho đến khi đúng
          
                year = inputForYear();
                
                month = inputForMonth();
          while (!isValidDate) {      
                System.out.print("Enter day: ");
                day = inputForPositiveInt();
                if (validator.checkValidDate(day, month, year)) {
                    isValidDate = true;
                } else {
                    System.out.println("=> Error: Invalid Date\n");
                }
        }
         joinDay = LocalDate.of(year, month,  day);
        return joinDay;
    }
    
    public static Status inputStatus(){
       int index=inputChoice(1, 4);
       return Status.values()[index-1];  
    }
    
    public static AttendanceStatus inputAttendance(){
        int index = inputChoice(1,3);
        return AttendanceStatus.values()[index-1];
    }
    
    public static double inputForPositiveDouble() {
        double result;
        while (true) {
            try {
                result = Double.parseDouble(sc.nextLine());

                if (result < 0) {
                    System.out.println("Input need to be >0");
                }
                else return result;

            } catch (NumberFormatException e) {
                System.out.println("Invalid value!");
            }
        }
    }
     public static int inputForPositiveInt() {
        int result;
        while (true) {
            try {
                result = Integer.parseInt(sc.nextLine());

                if (result < 0) {
                    System.out.println("Input need to be >0");
                }
                else return result;

            }
            catch (NumberFormatException e){
                System.out.println("Invalid value!");
                System.out.print("Enter again: ");
            }
        }
     }
     
      public static int inputForYear() {
        int result;
          Year currentYear =Year.now();  
        while (true) {
            try {
                 System.out.print("Enter year: ");
                result = Integer.parseInt(sc.nextLine());

                if (result < 1999) {
                    System.out.println("Year need to be >= 1999 ");
                }
                else if (result > currentYear.getValue()){
                    System.out.println("Year need to <=" + currentYear.getValue());
               }
                else return result;

            }
            catch (NumberFormatException e){
                System.out.println("Invalid value!");
                System.out.print("Please enter again: ");
            }
        }
     }
     public static int inputForMonth(){
         int month;
        
         while (true) {
            try {
                System.out.print("Enter month: ");
                month = Integer.parseInt(sc.nextLine());

                if (month <= 0 || month>12) {
                    System.out.println("Input need to be 1-12");
                }
               else return month;

            }
             catch (NumberFormatException e){
                System.out.println("Invalid value!");
            }
        }
     }

     public static int inputInt(String message){
        while(true){
            try{
                System.out.println(message);
                return Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Error: Invalid integer!");
            }
        }
     }
     public static void waitEnter() {
        System.out.println("Enter to continue!");
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
    }
    
    
}
