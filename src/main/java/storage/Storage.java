package storage;

import task.Deadline;
import task.Event;
import task.Task;
import tasklist.TaskList;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static final ArrayList<Task> arrTaskList = new ArrayList<>();
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveFile(TaskList taskList){
        try{
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Task task: taskList.getTaskList()) {
                bufferedWriter.write(task.toSaveString() + "\n");
            }
            bufferedWriter.close();
        } catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    public ArrayList<Task> load(){
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while((content = bufferedReader.readLine())!= null){
                if(content.charAt(0) == 'D'){
                    String details = content.substring(8).split(" | ", 2)[0];
                    String date = content.substring(8).split(" | ", 2)[1];
                    if(content.charAt(4) == '\u2713'){
                        (new Deadline(details, date)).markAsDone();
                    }
                    arrTaskList.add(new Deadline(details, date));
                }else if(content.charAt(0) == 'E') {
                    String details = content.substring(8).split(" | ", 2)[0];
                    String date = content.substring(8).split(" | ", 2)[1];
                    if (content.charAt(4) == '\u2713') {
                        (new Event(details, date)).markAsDone();
                    }
                    arrTaskList.add(new Event(details, date));
                }
            }
            fileReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
        return arrTaskList;
    }
}
