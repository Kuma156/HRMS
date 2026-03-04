package UserInterface;

import model.AttendanceStatus;
import model.Employee;
import inputUtils.input;


public class mySystem {

    public void pause() {
        inputUtils.input.waitEnter();
    }

    public void pauseAndClear() {
        pause();
        clearScreen();
    }
    
    public void Loading(){
          for(int i=0;i<=5;i++){
              try {
                  Thread.sleep(300);
              } catch (InterruptedException ex) {
              }
              System.out.print(".");
                      
          }
          System.out.println("");
      }

      public void clearScreen(){
      for(int i=0;i<50;i++){
          System.out.println("");
      }
   }


    public int askExitOption() {
        System.out.println("Do you want to save data before exiting? ");
        System.out.println("1. Yes || 0. No || 2. Cancel");
        return input.inputChoice(0, 2);
    }

    public int askClearDataOption() {
        System.out.println("Are you sure? ");
        System.out.println("1. Yes || 0. No");
        return input.inputChoice(0, 1);
    }

    public double askOverTimeHourIfPresent(AttendanceStatus status){
        if (status == model.AttendanceStatus.PRESENT) {
            System.out.print("Enter overtime hour: ");
            return input.inputForPositiveDouble();
        }else
            return 0;
    }

    public void showGoodbye() {
        System.out.print("Exiting program");
        Loading();
        System.out.println("See you again!");
        pause();
        clearScreen();
    }

    public void printEmployeeNotFound(String id) {
        System.out.println("There is no employee with this id " + id);
    }

    public void printEmployeeContext(String name, String id) {
        System.out.println("Employee name: " + name);
        System.out.println("Employee ID: " + id);
    }

    public void printSectionTitle(String title) {
        System.out.println("\n========== " + title + " ==========");
    }

    public void printEmployeeProfile(Employee employee) {
        printSectionTitle("Employee Profile");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Role: " + employee.getRole());
        System.out.println("Status: " + employee.getStatus());
        System.out.println("Join day: " + employee.getLocalDate());
        System.out.println("Basic salary: " + employee.getBasicSalary());
    }

    public void mainMenu(){
        System.out.println(".:============= EMPLOYEE MANAGEMENT =================:.");
        System.out.println("||     0. Exit programs (Save/NotSave)                ||");
        System.out.println("||     1. Add employee to List                        ||");
        System.out.println("||     2. Update employee info                        ||");
        System.out.println("||     3. Delete employee                             ||");
        System.out.println("||     4. Search Employee                             ||");
        System.out.println("||     5. Show all employee info                      ||");
        System.out.println("||     6. Attendance                                  ||");
        System.out.println("||     7. Salary                                      ||");
        System.out.println("||     8. Report by employee ID                       ||");
        System.out.println("||     9. Ultimate skill: Clear data                  ||");
        System.out.println(":|====================================================|:");
           System.out.print(":====> Your choice: ");               
    }
    public void printMenuUpdateInfo(){
        printSectionTitle("Update Employee Information");
        System.out.println("==========================");
        System.out.println("| 1. Update Name         |");
        System.out.println("| 2. Update Department   |");
        System.out.println("| 3. Update Role         |");
        System.out.println("| 4. Update Status       |");
        System.out.println("| 0. Return              |");
        System.out.println("==========================");
        System.out.print("==> Your choice: ");           
    }
    
    
    public void printUpdateStatusMenu() {
        System.out.println("---------------------------------");
        System.out.println("|         SELECT STATUS         |");
        System.out.println("---------------------------------");
        System.out.println("| 1. Active                     |");
        System.out.println("| 2. On Leave                   |");
        System.out.println("| 3. Leave                      |");
        System.out.println("| 4. Unknown                    |");
        System.out.println("---------------------------------");
        System.out.print("=> Enter your choice: ");
    }
    
     public void printAttendanceMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("|         SELECT ATTENDANCE STATUS         |");
        System.out.println("|------------------------------------------|");
        System.out.println("| 1. Present                               |");
        System.out.println("| 2. Absent                                |");
        System.out.println("| 3. Leave                                 |");
        System.out.println("--------------------------------------------");
        System.out.print("==> Enter your choice: ");
    }
    
    public void searchMenu(){
        System.out.println("=============================");
        System.out.println("| 1. Search By Name         |");
        System.out.println("| 2. Search By Role         |");
        System.out.println("| 3. Search By Department   |");
        System.out.println("=============================");
        System.out.print("==> Your choice: ");           
    }
    
    public void menuForCase6(){
        System.out.println("=========-Attendance-==========================================");
        System.out.println("| 0. Change employee ID                                       |");
        System.out.println("| 1. Add attendance for an employee                           |");
        System.out.println("| 2. Modify attendance status for a day (Only with added day) |");
        System.out.println("| 3. Modify OT hour for a day  (Present only)                 |");
        System.out.println("| 4. Print Attendance History - All history                   |");
        System.out.println("| 5. Print Attendance History - Only year                     |");
        System.out.println("| 6. Print Attendance History - Only month                    |");
        System.out.println("| 7. Print Attendance History - Specific date                 |");
        System.out.println("| 8. Print the number of absent day in month                  |");
        System.out.println("| 9. Print the number of present day in month                 |");
        System.out.println("| 10. Go back to main menu                                    |");
        System.out.println("===============================================================");
        //System.out.println("NOTE : 5 6 7 NOT COMPLETED - CAN OCCUR ERROR!");
        System.out.print("=> Your choice: ");
         
    }
    
    public void menuPartFullTime(){
                        System.out.println("======Which employee?======");
                        System.out.println("| 1. Part Time Employee   |");
                        System.out.println("| 2. Full Time Employee   |");
                        System.out.println("===========================");
                          System.out.print("=> Your choice: ");
    }

    public void menuForCase7(){
        System.out.println("==========MENU SALARY==========");
        System.out.println("|1. Add employee salary      ||");
        System.out.println("|2. Update employee salary   ||");
        System.out.println("|3. View personal salary     ||");
        System.out.println("|4. View all salary          ||");
        System.out.println("|0. Go back to main menu     ||");
        System.out.println("===============================");
        System.out.println("=> Your choice: ");
    }

}
