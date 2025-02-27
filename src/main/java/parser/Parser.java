package parser;

import command.ByeCommand;
import command.Command;
import command.DeadlineCommand;
import command.DeleteCommand;
import command.DoneCommand;
import command.EventCommand;
import command.FindCommand;
import command.ListCommand;
import command.TodoCommand;

import exception.DukeException;

import static common.Messages.ERROR_MESSAGE_RANDOM;
import static common.Messages.COMMAND_BYE;
import static common.Messages.COMMAND_DEADLINE;
import static common.Messages.COMMAND_DELETE;
import static common.Messages.COMMAND_DONE;
import static common.Messages.COMMAND_EVENT;
import static common.Messages.COMMAND_FIND;
import static common.Messages.COMMAND_LIST;
import static common.Messages.COMMAND_TODO;

/**
 * Making sense of the user input command.
 */
public class Parser {

    /**
     * Processes the different user input command.
     * @param userInputCommand String containing input command from user
     * @return the different command object corresponding to the user input
     * @throws DukeException if Duke cannot recognise the user input
     */
    public static Command parse(String userInputCommand) throws DukeException {
        if (userInputCommand.trim().equals(COMMAND_LIST)) {
            return new ListCommand(userInputCommand);
        } else if (userInputCommand.trim().equals(COMMAND_BYE)) {
            return new ByeCommand();
        } else if (userInputCommand.contains(COMMAND_DONE)) {
            return new DoneCommand(userInputCommand);
        } else if (userInputCommand.contains(COMMAND_DEADLINE)) {
            if (userInputCommand.trim().substring(0, 8).equals(COMMAND_DEADLINE)) {
                return new DeadlineCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_DELETE)) {
            if (userInputCommand.trim().substring(0, 6).equals(COMMAND_DELETE)) {
                return new DeleteCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_EVENT)) {
            if (userInputCommand.trim().substring(0, 5).equals(COMMAND_EVENT)) {
                return new EventCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_TODO)) {
            if (userInputCommand.trim().substring(0, 4).equals(COMMAND_TODO)) {
                return new TodoCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_FIND)) {
            if (userInputCommand.trim().substring(0, 4).equals(COMMAND_FIND)) {
                return new FindCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }
}
