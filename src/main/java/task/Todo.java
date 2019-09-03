package task;

/**
 * This class extend Task to model Todo objects.
 * Todo class, subclass of Task
 */
public class Todo extends Task {

    //Constructor for objects of class Todo
    public Todo(String description) {
        super(description);//super class constructor call to the Task(description) constructor
    }

    @Override
    public String toSaveString(){
        return "T" + super.toSaveString();
    }

    // Override the inherited toString to provide the proper implementation
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}