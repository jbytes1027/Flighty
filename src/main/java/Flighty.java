import database.Data;
import controller.UserManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flighty {
    private Scanner input;
    private UserManager userManager;
    private Data data;

    public static void main(final String[] args)
    {
        Flighty app = new Flighty();
        app.mainMenu();
    }

    public Flighty() {
        data = Data.getInstance();
        input = new Scanner(System.in);
        userManager = new UserManager(data);
    }

    public String promptString(String prompt) {
        print(prompt + "\n> ");
        String response = input.nextLine();

        return response;
    }

    private String toString(List<String> options) {
        String out = "";
        out += " [";
        for (int i = 0; i < options.size(); i++) {
            out += options.get(i);
            if (i != options.size() - 1) {
                out += "/";
            }
        }
        out += "]\n";

        return out;
    }

    /**
     * Prompts the user to choose an option
     * 
     * @param options entered string must be in
     * @return string user entered
     */
    public String promptOptions(String prompt, List<String> options) {
        print(prompt + toString(options));

        while (true) {
            print("> ");

            String response = input.nextLine();

            for (String option : options) {
                if (option.equalsIgnoreCase(response)) {
                    return option;
                }
            }

            println("Invalid option");
        }
    }

    /**
     * Prompts the user for a number
     * 
     * @param from entered number must be greater than or equal to
     * @param to entered number must be less than or equal to
     * @return number user entered
     */
    public int promptNumber(String prompt, int from, int to) {
        println(prompt);

        while (true) {
            print("> ");

            int response;

            try {
                response = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                println("Not a number");
                continue;
            }

            if (response >= from && response <= to) {
                return response;
            }

            println("Invalid option");
        }
    }

    private void print(String string) {
        System.out.print(string);
    }

    private void println(String string) {
        System.out.println(string);
    }

    /**
     * Prints a cool logo
     * @author rengotap
     */
    private void printHeader() {   
        println("88888888888  88  88               88" +'\n'
        +"88           88  "+"''"+"               88            ,d"+'\n'
        +"88           88                   88            88"+'\n'
        +"88aaaaa      88  88   ,adPPYb,d8  88,dPPYba,  MM88MMM  8b       d8"+'\n'
        +"88'''''      88  88  a8'    `Y88  88P'    '8a   88     `8b     d8'"+'\n'
        +"88           88  88  8b       88  88       88   88      `8b   d8'"+'\n'
        +"88           88  88  '8a,   ,d88  88       88   88,      `8b,d8'"+'\n'
        +"88           88  88   `'YbbdP'Y8  88       88   'Y888      Y88'"+'\n'
        +"                      aa,    ,88                           d8'"+'\n'
        +"                       'Y8bbdP'                           d8'"+'\n'
        +"                  Flight & Hotel Booking Program");
    }

    /**
     * Creates a numbered menu and promps the user to choose an option
     * 
     * @param options options for the user to choose from
     * @return chosen option from options
     */
    private String numberedMenu(String prompt, List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            println(String.format("%d. %s", i + 1, options.get(i)));
        }

        println("");

        int response = promptNumber(prompt, 1, options.size());
        return options.get(response - 1);
    }

    public void mainMenu() {
        final String OPTION_FLIGHT = "Find a Flight";
        final String OPTION_HOTEL = "Find a Hotel";
        final String OPTION_BOOKINGS = "Manage Bookings";
        final String OPTION_MANAGE_USER = "Manage User Profile";
        final String OPTION_CREATE_USER = "Create User Profile";
        final String OPTION_LOGOUT = "Logout";
        final String OPTION_EXIT = "Exit";

        List<String> options = new ArrayList<String>();

        // temp is a default guest account that saves data before making an account
        String currUserName = userManager.getCurrentUser().getName();
        if (userManager.getCurrentUser().getUsername().equals("temp")) {
            options.add(OPTION_CREATE_USER);
        } else {
            options.add(OPTION_MANAGE_USER);
            options.add(OPTION_LOGOUT);
        }

        options.add(OPTION_FLIGHT);
        options.add(OPTION_HOTEL);
        options.add(OPTION_BOOKINGS);
        options.add(OPTION_EXIT);

        printHeader();
        println("Welcome " + currUserName);
        String response = numberedMenu("Enter a Number", options);

        switch (response) {
            case OPTION_EXIT:
                exit();
                break;

            default:
                break;
        }
    }

    public void exit() {
        println("Exiting...");
        System.exit(0);
    }
}
