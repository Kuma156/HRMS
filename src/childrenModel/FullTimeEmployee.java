package childrenModel;
import java.time.LocalDate;
import model.Employee;

public class FullTimeEmployee extends Employee {
    private double overTimeRate = 80000;
    public FullTimeEmployee(String id, String name, String department, String role, LocalDate joinDay,double basicSalary) {
        super(id, name, department, role, joinDay,basicSalary);
    }

    @Override
    public double getOverTimeRate() {
        return overTimeRate;   
    }

    @Override
    public double calculateSalary(int absentCount, double overTimeHour) {
        getOverTimeRate(); getAbsentDeductionRate();
        double overTimePay = overTimeHour * overTimeRate;
        double absentDeduction = absentCount * getAbsentDeductionRate();

        return getBasicSalary() + overTimePay - absentDeduction;
    }  
    
}
