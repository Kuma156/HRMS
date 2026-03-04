package model;

import java.util.ArrayList;

/**
 *
 * @author The Miracle Invoker
 */
public class ReportFormForAttendance extends ReportForm {
    private ArrayList<Integer> absentDayInMonth;

    public ReportFormForAttendance(String id, String name, ArrayList<Integer> absentDayInMonth) {
        super(id, name);
        this.absentDayInMonth = absentDayInMonth;
    }

    public String getAbsentDayInMonth() {
        if (absentDayInMonth == null || absentDayInMonth.isEmpty())
            return "Therer are no absent day in month.";
        
        return absentDayInMonth.toString();
    }
    
    public int getTotalAbsentDays(){
        return absentDayInMonth != null ? absentDayInMonth.size() : 0;
    }
    
}
