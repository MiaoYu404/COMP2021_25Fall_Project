package clevis;

import clevis.system.Console;

import java.util.Arrays;
import java.util.Scanner;

/**
 * reading two files
 */
public class Application {

    /**
     * @param args input arguments
     */
    public static void main(String[] args){
        Clevis clevis = new Clevis();
        Console console = new Console();
        // Initialize and utilize the system
		Scanner scanner = new Scanner(System.in);
        while (true) {
            String inp = scanner.nextLine();
            String[] line = inp.split(" ");
            switch (line[0]) {
                case "add":
                    console.add(Arrays.copyOfRange(line, 1, line.length));
                    break;
                case "list":
                    console.list(line[1]);
                    break;
                case "quit":
                    console.quit();
            }
        }
    }

}
