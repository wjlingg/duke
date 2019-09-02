package parser;

import command.ByeCommand;
import command.Command;
import command.DeadlineCommand;
import command.EventCommand;
import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.util.ArrayList;

import static common.Messages.COMMAND_DEADLINE;
import static common.Messages.COMMAND_DELETE;
import static common.Messages.COMMAND_DONE;
import static common.Messages.COMMAND_EVENT;
import static common.Messages.COMMAND_EXIT_PROGRAM;
import static common.Messages.COMMAND_FIND;
import static common.Messages.COMMAND_GET_LIST;
import static common.Messages.COMMAND_TODO;

import static common.Messages.DIVIDER;
import static common.Messages.DISPLAYED_INDEX_OFFSET;

import static common.Messages.ERROR_MESSAGE_DEADLINE;
import static common.Messages.ERROR_MESSAGE_EMPTY_INDEX;
import static common.Messages.ERROR_MESSAGE_EMPTY_LIST;
import static common.Messages.ERROR_MESSAGE_EVENT;
import static common.Messages.ERROR_MESSAGE_GENERAL;
import static common.Messages.ERROR_MESSAGE_NOTFOUND;
import static common.Messages.ERROR_MESSAGE_INVALID_INDEX;
import static common.Messages.ERROR_MESSAGE_RANDOM;

import static common.Messages.MESSAGE_ADDED;
import static common.Messages.MESSAGE_BYE;
import static common.Messages.MESSAGE_DELETE;
import static common.Messages.MESSAGE_FIND;
import static common.Messages.MESSAGE_ITEMS1;
import static common.Messages.MESSAGE_ITEMS2;
import static common.Messages.MESSAGE_MARKED;
import static common.Messages.MESSAGE_FOLLOWUP_EMPTY_INDEX;
import static common.Messages.MESSAGE_FOLLOWUP_INVALID_INDEX;
import static common.Messages.MESSAGE_FOLLOWUP_NUll;
import static common.Messages.MESSAGE_TASKED;

public class Parser {
    private static Storage storage;
    private static final ArrayList<Task> myList = new ArrayList<>();
    private static final ArrayList<String> arrList = new ArrayList<>();

    public static Command parse(String userInputCommand) throws DukeException {
        if (userInputCommand.trim().equals(COMMAND_GET_LIST)) {
            System.out.println(DIVIDER + MESSAGE_TASKED);
            for (int i = 0; i < myList.size(); i++) {
                final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
                System.out.println(
                        "     " + displayIndex + ". " + myList.get(i)
                );
            }
            System.out.println(DIVIDER);
        } else if (userInputCommand.trim().equals(COMMAND_EXIT_PROGRAM)) {
            return new ByeCommand();
        } else if (userInputCommand.contains(COMMAND_DEADLINE)) {
            if (userInputCommand.trim().substring(0, 8).equals(COMMAND_DEADLINE)) {
                return new DeadlineCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        }else if (userInputCommand.contains(COMMAND_EVENT)) {
            if (userInputCommand.trim().substring(0, 5).equals(COMMAND_EVENT)) {
                return new EventCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        }else{
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
        throw new DukeException(ERROR_MESSAGE_RANDOM);
    }
}
