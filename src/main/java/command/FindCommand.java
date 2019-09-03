package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import static common.Messages.*;

public class FindCommand extends Command {

    public FindCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if(userInputCommand.trim().equals(COMMAND_FIND)){
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        }else if(userInputCommand.trim().charAt(4) == ' ') {
            String description = userInputCommand.trim().split("\\s", 2)[1];
            System.out.println(MESSAGE_FIND);
            for (int i = 0; i < taskList.findTask(description).size(); i++){
                System.out.println(taskList.findTask(description).get(i));
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
