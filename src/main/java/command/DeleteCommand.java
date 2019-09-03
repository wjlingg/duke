package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import static common.Messages.*;
import static common.Messages.ERROR_MESSAGE_RANDOM;

public class DeleteCommand extends Command {

    public DeleteCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException{
        if (userInputCommand.trim().equals(COMMAND_DELETE)) {
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        }else if(userInputCommand.trim().charAt(6) == ' '){
            String description = userInputCommand.trim().split("\\s",2)[1];
            //converting string to integer
            int index = Integer.parseInt(description);
            if (index > taskList.getSize()) {
                if (taskList.getSize() == 0) {
                    throw new DukeException(ERROR_MESSAGE_EMPTY_LIST);
                } else {
                    throw new DukeException(ERROR_MESSAGE_INVALID_INDEX + MESSAGE_FOLLOWUP_INVALID_INDEX + taskList.getSize());
                }
            } else {
                taskList.deleteTask(index - 1);
                storage.saveFile(taskList);
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
