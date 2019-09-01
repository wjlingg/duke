package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import static common.Messages.*;

public class DeadlineCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException{
        String msg = "";
        if(userInputCommand.trim().equals(COMMAND_DEADLINE)){
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll + DIVIDER);
        }else if(userInputCommand.trim().charAt(8) == ' '){
            String description = userInputCommand.trim().split("\\s",2)[1];
            if(description.contains(" /by ")) {
                String details = description.trim().split(" /by ", 2)[0];
                String date = description.trim().split(" /by ", 2)[1];
                if(details == null || date == null) {
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_DEADLINE + DIVIDER);
                }else{
                    taskList.addDeadlineTask(details, convertDate(date));
                    storage.saveFile(taskList);
                }
            }else{
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_DEADLINE + DIVIDER);
            }
        }else{
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
        }
    }

    private static String convertDate (String userInputDate) {
        String suffix = "";// st, nd, rd, th
        String extra = "";// am, pm
        String month = "";
        String dateline = "";//final conversion

        String[] monthArray = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        String date = userInputDate.split("\\s", 2)[0];
        String time = userInputDate.split("\\s", 2)[1];
        String min = time.substring(2, 4);//last 2 digits of the time
        String yr = date.split("/", 3)[2];

        int hr;
        int day = Integer.parseInt(date.split("/", 3)[0]);
        int mth = Integer.parseInt(date.split("/", 3)[1]);
        int first = Integer.parseInt(time.substring(0, 1));//first digit of the time
        int second = Integer.parseInt(time.substring(1, 2));//second digit of the time

        if(day == 1 || day == 21 || day == 31){
            suffix = "st";
        }else if(day == 2 || day == 22){
            suffix = "nd";
        }else if(day == 3 || day == 23){
            suffix = "rd";
        }else{
            suffix = "th";
        }

        //differentiating morning and afternoon
        extra = (first == 0 || (first == 1 && (second == 0 || second == 1))) ? "am" : "pm";
        int change = Integer.parseInt(time.substring(0, 2)) - 12;
        //converting the hours
        hr = (first == 0) ? ((second == 0) ? 12 : second) :
                ((first == 1) ? ((second <= 2) ? (first*10 + second) :
                        change) : change);
        //converting the month
        for (int i = 0; i < 12; i++){
            if(mth == i + 1){
                month = monthArray[i];
            }
        }

        dateline = day + suffix + " of " + month + " " + yr + ", " + hr + "." + min + extra;
        return dateline;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
