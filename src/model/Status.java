package model;

public enum Status {
    ACTIVE("Still active"),
    ON_LEAVE("Is on leave"),
    LEAVE("ALREADY LEAVED"),
    UNKNOWN("Unknown");
    
    
    private String label;
    
    Status(String label){
        this.label=label;
    }
    
    public String getLabel(){
        return label;
    } 
}
