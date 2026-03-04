package Main;

import Management.AttendanceManagement;
import UserInterface.mySystem;
import Management.EmployeeManagement;
import Management.SalaryManagement;
import Service.AttendanceServiceImpl;
import Service.EmployeeServiceImpl;
import Service.SalaryServiceImpl;
import Service.exceptions.ValidationException;
import Service.service.AttendanceService;
import Service.service.EmployeeService;
import Service.service.SalaryService;
import childrenModel.FullTimeEmployee;
import childrenModel.PartTimeEmployee;
import model.Employee;
import inputUtils.input;
import model.Status;


import saveLoad.LoadFile;
import saveLoad.SaveFile;
import saveLoad.clearData;

import java.time.LocalDate;
import java.util.TreeMap;

public class mainApp {

    private static EmployeeManagement employeeManagement ;
    private static AttendanceManagement attendanceManagement ;
    private static SalaryManagement salaryManagement = new SalaryManagement(new TreeMap<>());

    public static void main(String[] args) {
        LoadFile load = new LoadFile();
        employeeManagement = load.loadEmployeeList();
        attendanceManagement = load.loadAttendanceList();
        salaryManagement = load.loadSalaryList();
        if (employeeManagement == null) {
            employeeManagement = new EmployeeManagement();
        }
        if (attendanceManagement == null) {
            attendanceManagement = new AttendanceManagement();
        }
        if (salaryManagement == null) {
            salaryManagement = new SalaryManagement(new TreeMap<>());
        }
        
        int choice;
        mySystem p = new mySystem();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeManagement);
        AttendanceService attendanceService = new AttendanceServiceImpl(attendanceManagement, employeeManagement);
        SalaryService salaryService = new SalaryServiceImpl(salaryManagement);
        String id;
        String name;
        p.clearScreen();
        do {
            p.mainMenu();
            choice = input.inputChoice(0, 9);
            switch (choice) {
                case 0:
                    System.out.println("Do you want to save data before exiting? ");
                    System.out.println("1. Yes || 0. No || 2. Cancel");
                    loop: while(true){
                        int optionForCase0 = input.inputChoice(0, 2);
                        switch(optionForCase0){
                            case 1:
                                SaveFile save = new SaveFile(employeeManagement, attendanceManagement, salaryManagement);
                                System.out.println("Saved successfully!");
                                
                                System.out.print("Exiting program");
                                p.Loading();
                                System.out.println("See you again!");
                                p.pause();
                                p.clearScreen();
                                
                                return;
                            
                            case 0:
                                System.out.println("You denied to save!");
                                System.out.println("Exiting without saving!");
                                
                                System.out.print("Exiting program");
                                p.Loading();
                                System.out.println("See you again!");
                                p.pause();
                                p.clearScreen();

                                return;

                            case 2:
                                System.out.println("Cancel exiting.");
                                break loop;
                            
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                    }
                    break;
                    
                case 1:
                    // add employee
                    p.clearScreen();
                    System.out.println("--- ADD NEW EMPLOYEE ---");
                    id = input.inputId();
                    name = input.inputName();
                    String department = input.inputDepartment();
                    String role = input.inputRole();
                    System.out.println("===Enter join date");
                    LocalDate joinDay = input.inputDate();
                    System.out.print("===Enter basic Salary: ");
                    double basicSalary = input.inputForPositiveDouble();

                    p.clearScreen();
                    p.menuPartFullTime();
                    boolean isValidOption = true;
                    Employee tmpEmployee = null;
                    do {
                        int option = input.inputChoice(1, 2);
                        isValidOption = true;
                        switch (option) {
                            case 1:
                                tmpEmployee = new PartTimeEmployee(id, name, department, role, joinDay, basicSalary);
                                break;
                            case 2:
                                tmpEmployee = new FullTimeEmployee(id, name, department, role, joinDay, basicSalary);
                                break;
                            default:
                                System.out.println("Invalid choice");
                                isValidOption = false;
                        }
                    } while (!isValidOption);

                    try {
                        employeeService.addEmployee(tmpEmployee);
                        attendanceService.addEmployeeAttendanceProfile(id, name);
                        System.out.println("Added successfully");
                    } catch (ValidationException ex) {
                        System.out.println(ex.getMessage());
                    }
                    p.pause();
                    p.clearScreen();
                    break;
                case 2:
                    // update employee 
                    // Update name - status - role - department
                    p.clearScreen();
                    id = input.inputId();
                    Employee emp = employeeService.getById(id);
                    if (emp == null) {
                        System.out.println("There is no employee with this id " + id);
                        p.pause();
                        break;
                    }
                    p.printMenuUpdateInfo();
                    int optionForCase2 = input.inputChoice(1, 3);
                    String tmp = null;
                    loop: while(true){   
                    switch(optionForCase2){
                        case 1: //Update name
                            tmp = input.inputName();
                            employeeService.updateName(id,tmp);
                            System.out.print("Updating");
                            p.Loading();
                            
                            p.pause();
                            p.clearScreen();
                            System.out.println("Updated name successfully!");
                                        
                            break loop;
                        case 3:
                            tmp = input.inputRole();
                            employeeService.updateRole(id,tmp);
                            System.out.print("Updating");
                            p.Loading();
                            
                            p.pause();
                            p.clearScreen();
                            System.out.println("Updated role successfully!");
                                        
                            break loop;
                        case 2:
                            tmp = input.inputDepartment();
                            employeeService.updateDepartment(id,tmp);
                            System.out.print("Updating");
                            p.Loading();
                            
                            p.pause();
                            p.clearScreen();
                            System.out.println("Updated department successfully!");
                                        
                            break loop;
                        
                        case 4:
                            Status status = input.inputStatus();
                            employeeService.updateStatus(id,status);
                            System.out.print("Updating");
                            p.Loading();
                                
                            p.pause();
                            p.clearScreen();
                            System.out.println("Updated department successfully!");
                                            
                            break loop;
                        
                        default:
                            System.out.println("Invalid choice");
                            break;
                       }
                    }
                    break;

                case 3:
                    p.clearScreen();
                    // delete employee
                    System.out.println("--- DELETE EMPLOYEE ---");
                    System.out.print("Enter Employee ID to remove: ");
                    String removeId = input.inputId();
                    attendanceService.deleteEmployeeAttendance(removeId);
                    employeeService.removeEmployee(removeId);
                    
                    p.pause();
                    p.clearScreen();
                    break;

                case 4:
                    // search
                    p.clearScreen();
                    p.searchMenu();
                    int optionForCase4 = input.inputChoice(1, 3);
                    tmp = null;
                    loop: while(true){   
                    switch(optionForCase4){
                        case 1: 
                            tmp = input.inputName();
                            System.out.print("Searching");
                            p.Loading();
                            employeeService.searchEmployeeByName(tmp);
                            p.pause();
                            p.clearScreen();
                            break loop;
                        case 2:
                            tmp = input.inputRole();
                            System.out.print("Searching");
                            p.Loading();
                            employeeService.searchEmployeeByRole(tmp);
                            p.pause();
                            p.clearScreen();
                            break loop;
                        case 3:
                            tmp = input.inputDepartment();
                            System.out.print("Searching");
                            p.Loading();
                            employeeService.searchEmployeeByDepartment(tmp);
                            p.pause();
                            p.clearScreen();
                            break loop;
                        default:
                            System.out.println("Invalid choice");
                            break;
                       }
                    }
                     break;
                case 5:
                    //show all
                    employeeService.printAllEmployee();
                    p.pause();
                    p.clearScreen();
                    break;
                   
                case 6:
                    // modify attendance
                    p.clearScreen();
                    father_loop:while (true) {
                        id = input.inputId();
                        int index = employeeService.searchEmployeeByID(id);
                        if (index == -1) {
                            System.out.println("There is no employee with this id " + id);
                            p.pause();
                            break;
                        }
                        p.clearScreen();
                        loop: while (true) {
                        System.out.println("Employee name: "+employeeService.getEmployeeName(index));
                        System.out.println("Employee ID: "+id);
                        p.menuForCase6();
                        int optionForCase6 = input.inputChoice(0, 10);
                        int month,year;
                        switch (optionForCase6) {
                                case 0:
                                   p.clearScreen();
                                   break loop;
                                case 1:
                                    attendanceService.addAttendanceForEmployee(id);
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                case 2:
                                    attendanceService.modifyAttendanceForEmployee(id);
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                case 3:
                                    attendanceService.setOverTimeOfDay(id);
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                case 4:
                                    attendanceService.printAttendHistory(id);
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                case 5:
                                    year = input.inputForYear();
                                    attendanceService.printAttendHistory(id,year);
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                case 6:
                                    year = input.inputForYear();
                                    month = input.inputForMonth();
                                    attendanceService.printAttendHistory(id, month, year);
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                 case 7:
                                    LocalDate target = input.inputDate();
                                    attendanceService.printAttendHistory(id, target);
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                case 8:
                                    year = input.inputForYear();
                                    month = input.inputForMonth();
                                    System.out.println("The number of absent day in month "+ month+ "/"+ year +" : "
                                            +  attendanceService.getCountAbsent(id, month, year));
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                case 9:
                                    year = input.inputForYear();
                                    month = input.inputForMonth();
                                    attendanceService.getCountPresent(id,month,year);
                                     System.out.println("The number of present day in "+ month+ "/"+ year +" : "
                                            +  attendanceService.getCountPresent(id, month, year));
                                    p.pause();
                                    p.clearScreen();
                                    break;
                                    
                                case 10:
                                    p.pause();
                                    break father_loop;
                            }
                        }
                    }
                    p.clearScreen();
                    break;
                
                    case 7:
                        p.clearScreen();
                        salary_outer: while (true) {
                            id = input.inputId();
                            emp = null;
                            emp = employeeService.getById(id);
                            if (emp == null) {
                                System.out.println("There is no employee with this id " + id);
                                p.pause();
                                break;
                            }
                            if (employeeService.isActiveEmployee(id) == false) {
                                System.out.println("Only ACTIVE employees can be processed for salary.");
                                p.pause();
                                p.clearScreen();
                                break;
                            }
                    
                            p.clearScreen();
                            salary_inner: while (true) {
                                System.out.println("Employee name: " + emp.getName());
                                System.out.println("Employee ID: " + id);
                                p.menuForCase7();
                                int optionForCase7 = input.inputChoice(0, 4);
                    
                                switch (optionForCase7) {
                                    case 0:
                                        p.clearScreen();
                                        break salary_inner;
                    
                                    case 1: { // Add salary record (theo tháng/năm)
                                        int year = input.inputForYear();
                                        int month = input.inputForMonth();
                    
                                        int absent = attendanceService.getCountAbsent(id, month, year);
                                        double otHour = attendanceService.getOverTimeHourOfMonth(id, month, year);
                                        salaryService.addSalaryFromAttendance(emp, id, month, year, absent, otHour);
                                        System.out.println("Added salary successfully!");
                                        p.pause();
                                        p.clearScreen();
                                        break ;
                                    }
                    
                                    case 2: { // Update salary record
                                        int year = input.inputForYear();
                                        int month = input.inputForMonth();
                    
                                        int absent = attendanceService.getCountAbsent(id, month, year);
                                        double otHour = attendanceService.getOverTimeHourOfMonth(id, month, year);
                                        salaryService.updateSalaryFromAttendance(emp, id, month, year, absent, otHour);
                                        System.out.println("Updated salary successfully!");
                                        p.pause();
                                        p.clearScreen();
                                        break;
                                    }
                    
                                    case 3: { // View personal salary
                                        salaryService.printPersonalSalary(emp);
                                        int year = input.inputForYear();
                                        int month = input.inputForMonth();
                    
                                        double total = salaryService.calculateTotalSalary(id, month, year, emp.getBasicSalary());
                                        System.out.println("Total salary " + month + "/" + year + ": " + total);
                                        p.pause();
                                        p.clearScreen();
                                        break;
                                    }
                    
                                    case 4: { // View all salary
                                        int year = input.inputForYear();
                                        int month = input.inputForMonth();
                    
                                        salaryService.printAllSalary(employeeService.getAll(), month, year);
                                        p.pause();
                                        p.clearScreen();
                                        break;
                                    }
                                }
                            }
                        p.clearScreen();
                        break;
                    }
                    p.clearScreen();
                    break;
                
                    
                    
                    case 8:
                        p.clearScreen();
                        System.out.println("----- REPORT BY EMPLOYEE ID -----");
                        id = input.inputId();
                        Employee reportEmployee = employeeService.getById(id);

                        if (reportEmployee == null) {
                            System.out.println("There is no employee with this id " + id);
                            p.pause();
                            p.clearScreen();
                            break;
                        }

                        System.out.println("========== EMPLOYEE PROFILE ==========");
                        System.out.println("ID: " + reportEmployee.getId());
                        System.out.println("Name: " + reportEmployee.getName());
                        System.out.println("Department: " + reportEmployee.getDepartment());
                        System.out.println("Role: " + reportEmployee.getRole());
                        System.out.println("Status: " + reportEmployee.getStatus());
                        System.out.println("Join day: " + reportEmployee.getLocalDate());
                        System.out.println("Basic salary: " + reportEmployee.getBasicSalary());

                        System.out.println("\n========== ATTENDANCE HISTORY ==========");
                        attendanceService.printAttendHistory(id);

                        System.out.println("\n========== SALARY HISTORY ==========");
                        salaryService.printPersonalSalary(reportEmployee);

                        p.pause();
                        p.clearScreen();
                        break;

                    case 9:
                        System.out.println("Are you sure? ");
                        System.out.println("1. Yes || 0. No");
                    loop:
                        while (true) {
                            int optionForCase9 = input.inputChoice(0, 1);
                            switch (optionForCase9) {
                                case 1:
                                    clearData.clear();
                                    p.pause();
                                    p.clearScreen();
                                    break loop;
                                case 0:
                                    System.out.println("You denied to delete!");
                                    break loop;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                        }
                        
                default:
                    System.out.println("Invalid choice");
                    p.pause();
                    p.clearScreen();
                    break;
            }
        } while (true);

    }

}
