package task;

public class Deadline extends Task {

    protected String by; // private instance variables

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toSaveString(){
        return "D" + super.toSaveString() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}