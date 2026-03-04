package model;

/**
 *
 * @author The Miracle Invoker
 */
public abstract class ReportForm {
    protected final String id;
    protected String name;

    public ReportForm(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
