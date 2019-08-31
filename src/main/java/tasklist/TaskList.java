package tasklist;

import task.Task;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    public TaskList filter(String keyword) {
        TaskList filteredList = new TaskList();
        for (Task task : this) {
            if (task.getDescription().contains(keyword)) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

}