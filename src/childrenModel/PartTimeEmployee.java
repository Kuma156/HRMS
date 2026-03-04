package childrenModel;

import java.time.LocalDate;
import model.Employee;

public class PartTimeEmployee extends Employee{
    private final double overTimeRate = 50000;
    
    public PartTimeEmployee(String id, String name, String department, String role, LocalDate joinDay,double basicSalary) {
        super(id, name, department, role, joinDay,basicSalary);
    }

    @Override
    public double getOverTimeRate() {   
        return overTimeRate;
    }

    public double calculateSalary(int absentCount, double overTimeHour){
        getOverTimeRate(); getAbsentDeductionRate();

        double overTimePay = overTimeHour * overTimeRate;
        double absentDeduction = absentCount * getAbsentDeductionRate();

        return getBasicSalary() + overTimePay - absentDeduction;
    }
}
