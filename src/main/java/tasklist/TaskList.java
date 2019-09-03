package tasklist;

import exception.DukeException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.util.ArrayList;

import static common.Messages.*;

public class TaskList {

    private ArrayList<Task> taskList;
    private static String msg = "";

    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<String> findTask(String description) throws DukeException{
        ArrayList<String> arrFind = new ArrayList<>();
        for (int i = 0; i < getSize(); i++){
            if(taskList.get(i).getDescription().contains(description)) {
                arrFind.add(taskList.get(i).toString());
            }
        }
        if(arrFind.isEmpty()){
            throw new DukeException(ERROR_MESSAGE_NOTFOUND);
        }else {
            return arrFind;
        }
    }

    public ArrayList<String> listTask(){
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++){
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayIndex + ". " + taskList.get(i));
        }
        return arrList;
    }

    public int getSize(){
        return taskList.size();
    }

    public void addDeadlineTask(String description, String by){
        taskList.add(new Deadline(description, by));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    public void addEventTask(String description, String at) {
        taskList.add(new Event(description, at));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    public void addTodoTask(String description) {
        taskList.add(new Todo(description));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    public void doneTask(int i){
        taskList.get(i).markAsDone();
        System.out.println(MESSAGE_MARKED + "       " + taskList.get(i));
    }

    public void deleteTask(int i){
        if (taskList.size() - 1 <= 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + taskList.get(i) + "\n" + MESSAGE_ITEMS1 + (taskList.size() - 1) + msg);
        taskList.remove(taskList.get(i));
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}