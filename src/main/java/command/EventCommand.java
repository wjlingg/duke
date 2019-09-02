package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import static common.Messages.*;

public class EventCommand extends Command {
    public EventCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException{
        if (userInputCommand.trim().equals(COMMAND_EVENT)) {
            throw new DukeException(ERROR_MESSAGE_EVENT);
        }else if(userInputCommand.trim().charAt(5) == ' '){
            String description = userInputCommand.trim().split("\\s",2)[1];
            if(description.contains(" /at ")){
                String details = description.trim().split(" /at ", 2)[0];
                String date = description.trim().split(" /at ", 2)[1];
                if(details == null || date == null){
                    throw new DukeException(ERROR_MESSAGE_EVENT);
                }else{
                    taskList.addEventTask(details, date);
                    storage.saveFile(taskList);
                }
            }else{
                throw new DukeException(ERROR_MESSAGE_EVENT);
            }
        }else{
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
