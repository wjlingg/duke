package ui;

import java.util.Scanner;

import static common.Messages.*;

public class Ui {

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Display welcome message of the program
     */
    public void showWelcome() {
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

    public void showGoodbye(){
        System.out.println(DIVIDER + MESSAGE_BYE + DIVIDER);
    }

    public String readCommand(){
        return SCANNER.nextLine();
    }

    public void showLine(){
        System.out.print(DIVIDER);
    }

    public void showLoadingError(){
        System.out.println(DIVIDER + ERROR_MESSAGE_LOADING + filePath + "\n" + DIVIDER);
    }

    public void showError(String errorMessage){
        System.out.println(errorMessage);
    }
}
