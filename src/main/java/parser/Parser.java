package parser;

import command.Command;
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

    public static Command parse(String fullCommand) throws DukeException {
        if (fullCommand.trim().equals(COMMAND_GET_LIST)) {
            System.out.println(DIVIDER + MESSAGE_TASKED);
            for (int i = 0; i < myList.size(); i++) {
                final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
                System.out.println(
                        "     " + displayIndex + ". " + myList.get(i)
                );
            }
            System.out.println(DIVIDER);
        } else if (fullCommand.trim().equals(COMMAND_EXIT_PROGRAM)) {
            System.out.println(DIVIDER + MESSAGE_BYE + DIVIDER);
            System.exit(0);
        } else if (fullCommand.contains(COMMAND_DONE)) {
            if(fullCommand.trim().substring(0, 4).equals(COMMAND_DONE)) {
                commandDone(fullCommand);
            }else{
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
            }
        } else if (fullCommand.contains(COMMAND_TODO)) {
            if(fullCommand.trim().substring(0, 4).equals(COMMAND_TODO)) {
                commandTodo(fullCommand);
            }else{
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
            }
        } else if (fullCommand.contains(COMMAND_DEADLINE)) {
            if(fullCommand.trim().substring(0, 8).equals(COMMAND_DEADLINE)) {
                commandDeadline(fullCommand);
            }else{
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
            }
        } else if (fullCommand.contains(COMMAND_EVENT)) {
            if (fullCommand.trim().substring(0, 5).equals(COMMAND_EVENT)) {
                commandEvent(fullCommand);
            } else {
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
            }
        }else if (fullCommand.contains(COMMAND_DELETE)) {
            if(fullCommand.trim().substring(0, 6).equals(COMMAND_DELETE)) {
                commandDelete(fullCommand);
            }else{
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
            }
        } else if (fullCommand.contains(COMMAND_FIND)) {
            if (fullCommand.trim().substring(0, 4).equals(COMMAND_FIND)) {
                commandFind(fullCommand);
            } else {
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
            }
        }else{
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
        }
    }

    private static void commandDone(String userInputString) throws DukeException{
        if(userInputString.trim().equals(COMMAND_DONE)){
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX + DIVIDER);
        }else if(userInputString.trim().charAt(4) == ' '){
            String description = userInputString.trim().split("\\s",2)[1];
            //converting string to integer
            int index = Integer.parseInt(description);
            if(index > myList.size()){
                System.out.print(DIVIDER);
                if(myList.size() == 0){
                    throw new DukeException(ERROR_MESSAGE_EMPTY_LIST + DIVIDER);
                }else {
                    throw new DukeException(ERROR_MESSAGE_INVALID_INDEX + MESSAGE_FOLLOWUP_INVALID_INDEX + myList.size() + "\n" + DIVIDER);
                }
            }else{
                //marking targeted item as completed
                myList.get(index - 1).markAsDone();
                storage.saveFile(userInputString.trim().substring(0, 4), arrList.size() - myList.size() + index - 1, "");
                System.out.println(
                        DIVIDER + MESSAGE_MARKED +
                                "       " + myList.get(index - 1) + "\n" + DIVIDER
                );
            }
        }else{
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
        }
    }

    private static void commandTodo(String userInputString) throws DukeException{
        String msg = "";
        if(userInputString.trim().equals(COMMAND_TODO)){
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll + DIVIDER);
        }else if(userInputString.trim().charAt(4) == ' '){
            String description = userInputString.trim().split("\\s",2)[1];
            myList.add(new Todo(description));
            int index = myList.size();
            if (index == 1) {
                msg = " task in the list.\n";
            } else {
                msg = MESSAGE_ITEMS2;
            }
            System.out.println(
                    DIVIDER + MESSAGE_ADDED +
                            "       " + myList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg +
                            DIVIDER
            );
            storage.saveFile(userInputString.trim().substring(0, 4), index, myList.get(index - 1).toSaveString());
        }else{
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
        }
    }

    private static void commandDeadline(String userInputString) throws DukeException {
        String msg = "";
        if(userInputString.trim().equals(COMMAND_DEADLINE)){
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll + DIVIDER);
        }else if(userInputString.trim().charAt(8) == ' '){
            String description = userInputString.trim().split("\\s",2)[1];
            if(description.contains(" /by ")) {
                String details = description.trim().split(" /by ", 2)[0];
                String date = description.trim().split(" /by ", 2)[1];
                if(details == null || date == null) {
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_DEADLINE + DIVIDER);
                }else{
                    myList.add(new Deadline(details, convertDate(date)));
                    int index = myList.size();
                    if (index == 1) {
                        msg = " task in the list.\n";
                    } else {
                        msg = MESSAGE_ITEMS2;
                    }
                    System.out.println(
                            DIVIDER + MESSAGE_ADDED +
                                    "       " + myList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg +
                                    DIVIDER
                    );
                    storage.saveFile(userInputString.trim().substring(0, 8), index, myList.get(index - 1).toSaveString());
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

    private static void commandEvent(String userInputString) throws DukeException {
        String msg = "";
        if (userInputString.trim().equals(COMMAND_EVENT)) {
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_EVENT + DIVIDER);
        }else if(userInputString.trim().charAt(5) == ' '){
            String description = userInputString.trim().split("\\s",2)[1];
            if(description.contains(" /at ")){
                String details = description.trim().split(" /at ", 2)[0];
                String date = description.trim().split(" /at ", 2)[1];
                if(details == null || date == null){
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_EVENT + DIVIDER);
                }else{
                    myList.add(new Event(details.trim(), date.trim()));
                    int index = myList.size();
                    if (index == 1) {
                        msg = " task in the list.\n";
                    } else {
                        msg = MESSAGE_ITEMS2;
                    }
                    System.out.println(
                            DIVIDER + MESSAGE_ADDED +
                                    "       " + myList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg +
                                    DIVIDER
                    );
                    storage.saveFile(userInputString.trim().substring(0, 5), index, myList.get(index - 1).toSaveString());
                }
            }else{
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_EVENT + DIVIDER);
            }
        }else{
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
        }
    }

    private static void commandDelete(String userInputString) throws DukeException {
        String msg = "";
        if (userInputString.trim().equals(COMMAND_DELETE)) {
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX + DIVIDER);
        } else if (userInputString.trim().charAt(6) == ' ') {
            String description = userInputString.trim().split("\\s", 2)[1];
            //converting string to integer
            int index = Integer.parseInt(description);
            if (index > myList.size()) {
                System.out.print(DIVIDER);
                if (myList.size() == 0) {
                    throw new DukeException(ERROR_MESSAGE_EMPTY_LIST + DIVIDER);
                } else {
                    throw new DukeException(ERROR_MESSAGE_INVALID_INDEX + MESSAGE_FOLLOWUP_INVALID_INDEX + myList.size() + "\n" + DIVIDER);
                }
            } else {
                Task removed = myList.get(index - 1);
                //save before remove if not the input index for storage.saveFile() will be wrong
                //but also can insert as (arrList.size() - myList.size() + index - 2)
                storage.saveFile(userInputString.trim().substring(0, 6), arrList.size() - myList.size() + index - 1, "");
                myList.remove(removed);
                if (myList.size() == 1) {
                    msg = " task in the list.\n";
                } else {
                    msg = MESSAGE_ITEMS2;
                }
                System.out.println(
                        DIVIDER + MESSAGE_DELETE +
                                "       " + removed + "\n" + MESSAGE_ITEMS1 + myList.size() + msg +
                                DIVIDER
                );
            }
        } else {
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
        }
    }
    private static void commandFind(String userInputString) throws DukeException {
        ArrayList<String> temp = new ArrayList<>();
        if(userInputString.trim().equals(COMMAND_FIND)){
            System.out.print(DIVIDER);
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll + DIVIDER);
        }else if(userInputString.trim().charAt(4) == ' ') {
            String description = userInputString.trim().split("\\s", 2)[1];
            for(int i = 0; i < arrList.size(); i++){
                String same = arrList.get(i);
                if(same.contains(description)){
                    temp.add(same);
                }else if(i == arrList.size() - 1){
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_NOTFOUND + DIVIDER);
                }
            }
            System.out.println(DIVIDER + MESSAGE_FIND);
            for (int i = 0; i < temp.size(); i++) {
                final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
                System.out.println(
                        "     " + displayIndex + ". " + temp.get(i)
                );
            }
            System.out.println(DIVIDER);
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
}
