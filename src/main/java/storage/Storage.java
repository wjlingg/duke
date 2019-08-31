package storage;

import task.Task;
import tasklist.TaskList;

import java.io.*;
import java.util.ArrayList;

import static common.Messages.*;

public class Storage {
    private static final ArrayList<String> arrList = new ArrayList<>();
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveFile(String command, int index, String items){
        if(command.equals(COMMAND_DONE)) {
            ArrayList<String> temp = new ArrayList<>();
            String tempStr = "";
            for (int i = 0; i < arrList.size(); i++){
                if(i == index){
                    tempStr = arrList.get(i).replace("\u2718", "\u2713");
                    temp.add(tempStr);
                }else{
                    temp.add(arrList.get(i));
                }
            }
            arrList.clear();
            arrList.addAll(temp);
        }else if(command.equals(COMMAND_DELETE)){
            arrList.remove(index);
        }else{
            arrList.add(items);
        }
        try{
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(String str: arrList) {
                bufferedWriter.write(str + "\n");
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
                arrList.add(content);
            }
            return arrList;
            fileReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
    }
}
