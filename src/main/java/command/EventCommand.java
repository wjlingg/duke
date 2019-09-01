package command;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

public class EventCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
