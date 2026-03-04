package model;

/**
 *
 * @author The Miracle Invoker
 */
public class ReportFormForSalary extends ReportForm {
    private double totalSalary;

    public ReportFormForSalary(String id, String name, double totalSalary ) {
        super(id, name);
        this.totalSalary = totalSalary;
    }

    public double getTotalSalary() {
        return totalSalary;
    }
    
}
