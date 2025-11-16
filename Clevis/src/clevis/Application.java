package clevis;

import clevis.system.Console;

import java.util.Scanner;

/**
 * reading two files
 */
public class Application {
    private static Console console = new Console();

    /**
     * @param args input arguments
     */
    public static void main(String[] args){

        // Initialize and utilize the system
		Scanner scanner = new Scanner(System.in);
        while (true) {
            String inp = scanner.nextLine();
            console.readOperation(inp);
        }
    }

    /**
     * @return              the console
     */
    public Console console() {
        return console;
    }

}
