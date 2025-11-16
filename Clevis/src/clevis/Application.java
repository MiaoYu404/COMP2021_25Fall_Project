package clevis;

import clevis.system.Console;
import clevis.system.Logger;

import java.util.Scanner;

/**
 * reading two files
 */
public class Application {
    private static Console console;
    private static Logger logger;

    /**
     * @param args input arguments
     */
    public static void main(String[] args){
        parseArgs(args);
        console = new Console();
        // Initialize and utilize the system
		Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String inp = scanner.nextLine();
                if (inp.isEmpty()) continue;
                logger.log(inp);
                console.readOperation(inp);
            }
        } finally {
            console.printInfo("System ended.");
        }
    }

    /**
     * @return              the console
     */
    public Console console() {
        return console;
    }

    private static void parseArgs(String[] args) {
        String htmlPath = null, txtPath = null;

        for (int i = 0; i < args.length; i++) {
            if ("-html".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                htmlPath = args[++i];
            } else if ("-txt".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                txtPath = args[++i];
            }
        }

        if (htmlPath == null || txtPath == null) {
            System.err.println("Usage: java hk.edu.polyu.comp.comp2021.clevis.Application " +
                    "-html <htmlFile> -txt <txtFile>");
            System.exit(1);
        }

        logger = new Logger(htmlPath, txtPath);
    }

    /**
     * @return      logger
     */
    public static Logger logger() {
        return logger;
    }
}
