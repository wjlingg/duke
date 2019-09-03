package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import static common.Messages.*;

public class ListCommand extends Command {

    public ListCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        System.out.println(MESSAGE_TASKED);
        for (int i = 0; i < taskList.listTask().size(); i++){
            System.out.println(taskList.listTask().get(i));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
