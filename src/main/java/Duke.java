import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final ArrayList<Task> myList = new ArrayList<>();
    private static final ArrayList<String> arrList = new ArrayList<>();

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int DISPLAYED_INDEX_OFFSET = 1;

    private static final String filePath = "C:\\Users\\Wen Jian\\Desktop\\duke\\src\\main\\data\\duke.txt";

    private static final String MESSAGE_TASKED = "     Here are the tasks in your list:";
    private static final String MESSAGE_MARKED = "     Nice! I've marked this task as done:\n";
    private static final String MESSAGE_ADDED = "     Got it. I've added this task:\n";
    private static final String MESSAGE_DELETE = "     Noted. I've removed this task:\n";
    private static final String MESSAGE_FIND = "     Here are the matching tasks in your list:\n";
    private static final String MESSAGE_ITEMS1 = "     Now you have ";
    private static final String MESSAGE_ITEMS2 = " tasks in the list.\n";
    private static final String MESSAGE_BYE = "     Bye. Hope to see you again soon!\n";
    private static final String MESSAGE_FOLLOWUP_INVALID_INDEX = "     Kindly enter command with index not more than ";
    private static final String MESSAGE_FOLLOWUP_EMPTY_INDEX = "       Kindly enter the command again with an index.\n";
    private static final String MESSAGE_FOLLOWUP_NUll = "       Kindly enter the command again with a description.\n";

    private static final String ERROR_MESSAGE_GENERAL = "       ☹ OOPS!!! The description cannot be empty.\n";
    private static final String ERROR_MESSAGE_EVENT = "       ☹ OOPS!!! Please specify the event details in this format: \n         event [event description] /at [event time or venue]\n";
    private static final String ERROR_MESSAGE_DEADLINE = "       ☹ OOPS!!! Please specify the deadline in this format: \n         deadline [event description] /by [day/month/year time]\n         Eg: 28/8/2019 2359 (For date and time format)\n";
    private static final String ERROR_MESSAGE_INVALID_DATE = "       ☹ OOPS!!! Please specify the date and time in this format: \n         [day/month/year time] Eg: 28/8/2019 2359\n";
    private static final String ERROR_MESSAGE_RANDOM = "     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(\n";
    private static final String ERROR_MESSAGE_EMPTY_LIST = "       ☹ OOPS!!! The list is empty.\n       Kindly add a task.\n";
    private static final String ERROR_MESSAGE_EMPTY_INDEX = "       ☹ OOPS!!! The index cannot be empty.\n";
    private static final String ERROR_MESSAGE_INVALID_INDEX = "     Invalid index entered.\n";
    private static final String ERROR_MESSAGE_NOTFOUND = "     ☹ OOPS!!! I'm sorry, but there is no matching tasks in your list\n";

    private static final String COMMAND_GET_LIST = "list";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_EXIT_PROGRAM = "bye";
    private static final String DIVIDER = "   ____________________________________________________________\n";

    /**
     * Main entry point of the application.
     * Initializes the application and starts the interaction with the user.
     */
    public static void main(String[] args){
        showHelloMessage();
        readFile();
        while (true) {
            String userCommand = getUserInput();
            executeCommand(userCommand);
        }
    }

    /**
     * Display welcome message of the program
     */
    private static void showHelloMessage() {

        String logo =
                "      ___         _        \n"
                        + "     |  _ \\ _   _| | _____ \n"
                        + "     | | | | | | | |/ / _ \\\n"
                        + "     | |_| | |_| |   <  __/\n"
                        + "     |____/ \\__,_|_|\\_\\___|\n"
                        + "\n";

        System.out.println(
                DIVIDER + logo +
                        "     Hello! I'm Duke\n" +
                        "     What can I do for you?\n" +
                        DIVIDER
        );
    }

    private static void readFile(){
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while((content = bufferedReader.readLine())!= null){
                arrList.add(content);
            }
            fileReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
    }

    private static void saveFile(String command, int index, String items){
        if(command.equals(COMMAND_DONE)) {
            ArrayList<String> temp = new ArrayList<>();
            String tempStr = "";
            for (int i = 0; i < arrList.size(); i++){
                if(i == index){
                    tempStr = arrList.get(i).replace("\u2718", "\u2713");
                    temp.add(tempStr);
                }else{
                    temp.add(arrList.get(i));
                }
            }
            arrList.clear();
            arrList.addAll(temp);
        }else if(command.equals(COMMAND_DELETE)){
            arrList.remove(index);
        }else{
            arrList.add(items);
        }
        try{
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(String str: arrList) {
                bufferedWriter.write(str + "\n");
            }
            bufferedWriter.close();
        } catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Executes the command as specified by the {@code userInputString}
     * Adding items to the list
     * Display items in the list
     * Mark items in the list as completed
     * Exit program as requested
     *
     * @param userInputString raw input from user
     */
    private static void executeCommand(String userInputString) {
        try {
            if (userInputString.trim().equals(COMMAND_GET_LIST)) {
                System.out.println(DIVIDER + MESSAGE_TASKED);
                for (int i = 0; i < myList.size(); i++) {
                    final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
                    System.out.println(
                            "     " + displayIndex + ". " + myList.get(i)
                    );
                }
                System.out.println(DIVIDER);
            } else if (userInputString.trim().equals(COMMAND_EXIT_PROGRAM)) {
                System.out.println(DIVIDER + MESSAGE_BYE + DIVIDER);
                System.exit(0);
            } else if (userInputString.contains(COMMAND_DONE)) {
                if(userInputString.trim().substring(0, 4).equals(COMMAND_DONE)) {
                    commandDone(userInputString);
                }else{
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
                }
            } else if (userInputString.contains(COMMAND_TODO)) {
                if(userInputString.trim().substring(0, 4).equals(COMMAND_TODO)) {
                    commandTodo(userInputString);
                }else{
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
                }
            } else if (userInputString.contains(COMMAND_DEADLINE)) {
                if(userInputString.trim().substring(0, 8).equals(COMMAND_DEADLINE)) {
                    commandDeadline(userInputString);
                }else{
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
                }
            } else if (userInputString.contains(COMMAND_EVENT)) {
                if (userInputString.trim().substring(0, 5).equals(COMMAND_EVENT)) {
                    commandEvent(userInputString);
                } else {
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
                }
            }else if (userInputString.contains(COMMAND_DELETE)) {
                if(userInputString.trim().substring(0, 6).equals(COMMAND_DELETE)) {
                    commandDelete(userInputString);
                }else{
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
                }
            } else if (userInputString.contains(COMMAND_FIND)) {
                if (userInputString.trim().substring(0, 4).equals(COMMAND_FIND)) {
                    commandFind(userInputString);
                } else {
                    System.out.print(DIVIDER);
                    throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
                }
            }else{
                System.out.print(DIVIDER);
                throw new DukeException(ERROR_MESSAGE_RANDOM + DIVIDER);
            }
        }catch(DukeException e){
            System.out.println(e.getMessage());
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
                saveFile(userInputString.trim().substring(0, 4), arrList.size() - myList.size() + index - 1, "");
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
            saveFile(userInputString.trim().substring(0, 4), index, myList.get(index - 1).toSaveString());
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
                    saveFile(userInputString.trim().substring(0, 8), index, myList.get(index - 1).toSaveString());
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
                    saveFile(userInputString.trim().substring(0, 5), index, myList.get(index - 1).toSaveString());
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
                //save before remove if not the input index for savefile() will be wrong
                //but also can insert as (arrList.size() - myList.size() + index - 2)
                saveFile(userInputString.trim().substring(0, 6), arrList.size() - myList.size() + index - 1, "");
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

    /**
     * Reads the text entered by the user.
     *
     * @return full line entered by the user
     */
    private static String getUserInput() {
        String inputLine = SCANNER.nextLine();
        return inputLine;
    }
}