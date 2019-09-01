package tasklist;

import exception.DukeException;
import task.Deadline;
import task.Task;

import java.util.ArrayList;

import static common.Messages.*;

public class TaskList {

    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public TaskList findTask(String keyword) throws DukeException{
        TaskList tempList = new TaskList();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getDescription().contains(keyword)) {
                tempList.addTask(taskList.get(i));
            }
        }
        return tempList;
    }

    public void addTask(Task task){
        taskList.add(task);
    }

    public void addDeadlineTask(String description, String by){
        taskList.add(new Deadline(description, by));
        String msg = "";
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.\n";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(
                DIVIDER + MESSAGE_ADDED +
                        "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg +
                        DIVIDER
        );
    }

    public void doneTask(int i){
        taskList.get(i).markAsDone();
    }

    public void deleteTask(int i){
        taskList.remove(taskList.get(i));
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}