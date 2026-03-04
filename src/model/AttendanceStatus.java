package model;


public enum AttendanceStatus{
    PRESENT("Present"),
    ABSENT("Absent"),
    LEAVE("Leave");
    
    private final String label;

    private AttendanceStatus(String label) {
        this.label = label;
        
    }
    
    public String getLabel(){
        return label;
    }    
}
