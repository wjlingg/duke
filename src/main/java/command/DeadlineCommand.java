package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import java.text.SimpleDateFormat;

import static common.Messages.*;

public class DeadlineCommand extends Command {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy HHmm");
    public DeadlineCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException{
        if(userInputCommand.trim().equals(COMMAND_DEADLINE)){
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        }else if(userInputCommand.trim().charAt(8) == ' '){
            String description = userInputCommand.trim().split("\\s",2)[1];
            if(description.contains(" /by ")) {
                String details = description.trim().split(" /by ", 2)[0];
                String date = description.trim().split(" /by ", 2)[1];
                if(details == null || date == null) {
                    throw new DukeException(ERROR_MESSAGE_DEADLINE);
                }else{
                    if(isParseDate(date)) {
                        taskList.addDeadlineTask(details, date);
                        storage.saveFile(taskList);
                    }else{
                        throw new DukeException(ERROR_MESSAGE_INVALID_DATE);
                    }
                }
            }else{
                throw new DukeException(ERROR_MESSAGE_DEADLINE);
            }
        }else{
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    private static boolean isParseDate(String dateStr) {
        try {
            simpleDateFormat.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
