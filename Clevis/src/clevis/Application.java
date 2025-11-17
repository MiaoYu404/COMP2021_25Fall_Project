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

    private static String htmlPath, txtPath;

    /**
     * Boost the Application
     * @param record        whether log files will be record
     * @param args          arguments
     */
    public static void Boost(boolean record, String[] args) {
        if (record) parseArgs(args);
        logger = new Logger(htmlPath, txtPath);
        console = new Console();
    }

    /**
     * @param args input arguments
     */
    public static void main(String[] args){
        Boost(true, args);
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
    }

    /**
     * @return      logger
     */
    public static Logger logger() {
        return logger;
    }
}
