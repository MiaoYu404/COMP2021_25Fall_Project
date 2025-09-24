//
//package hk.edu.polyu.comp.comp2021.clevis;
//
import util.BoundingBox;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * The main application class for CLEVIS vector graphics editor.
 */
public class Application {
    private static final String HTML_LOG_FILE = "-html";
    private static final String TXT_LOG_FILE = "-txt";
    private static GraphicsManager graphicsManager = new GraphicsManager();
    private static String htmlLogPath = null;
    private static String txtLogPath = null;

    /**
     * Main method to start the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        parseArguments(args);
        startCommandLineInterface();
    }

    /**
     * Parse command line arguments.
     * @param args command line arguments
     */
    private static void parseArguments(String[] args) {
        // 默认值设置为log目录下带时间戳的文件
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        htmlLogPath = "log/log_" + timestamp + ".html";
        txtLogPath = "log/log_" + timestamp + ".txt";
        for (int i = 0; i < args.length; i++) {
            if (HTML_LOG_FILE.equals(args[i]) && i + 1 < args.length) {
                htmlLogPath = args[i + 1];
                i++;
            } else if (TXT_LOG_FILE.equals(args[i]) && i + 1 < args.length) {
                txtLogPath = args[i + 1];
                i++;
            }
        }
        initializeLogFiles();
    }

    /**
     * Initialize log files.
     */
    private static void initializeLogFiles() {
        // 确保log目录存在
        File logDir = new File("log");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        if (htmlLogPath != null) {
            try (PrintWriter out = new PrintWriter(new FileWriter(htmlLogPath))) {
                out.println("<html><body><table border='1'>");
                out.println("<tr><th>Index</th><th>Command</th></tr>");
            } catch (IOException e) {
                System.err.println("Failed to initialize HTML log file: " + e.getMessage());
            }
        }
        if (txtLogPath != null) {
            try {
                new FileWriter(txtLogPath, false).close(); // Clear file
            } catch (IOException e) {
                System.err.println("Failed to initialize TXT log file: " + e.getMessage());
            }
        }
    }

    /**
     * Start the command line interface.
     */
    private static void startCommandLineInterface() {
        Scanner scanner = new Scanner(System.in);
        int commandIndex = 1;

        System.out.println("CLEVIS Vector Graphics Editor");
        System.out.println("Type 'quit' to exit.");

        while (true) {
            System.out.print("Command: ");
            String command = scanner.nextLine();
            logCommand(commandIndex++, command);

            if ("quit".equalsIgnoreCase(command.trim())) {
                System.out.println("Exiting CLEVIS...");
                closeLogFiles();
                break;
            }

            executeCommand(command);
        }
        scanner.close();
    }

    /**
     * Log command to files.
     * @param index command index
     * @param command command string
     */
    private static void logCommand(int index, String command) {
        if (htmlLogPath != null) {
            try (PrintWriter out = new PrintWriter(new FileWriter(htmlLogPath, true))) {
                out.printf("<tr><td>%d</td><td>%s</td></tr>\n", index, command.replace("<", "&lt;").replace(">", "&gt;"));
            } catch (IOException e) {
                System.err.println("Failed to write to HTML log file: " + e.getMessage());
            }
        }
        if (txtLogPath != null) {
            try (PrintWriter out = new PrintWriter(new FileWriter(txtLogPath, true))) {
                out.println(command);
            } catch (IOException e) {
                System.err.println("Failed to write to TXT log file: " + e.getMessage());
            }
        }
    }

    /**
     * Close log files properly.
     */
    private static void closeLogFiles() {
        if (htmlLogPath != null) {
            try (PrintWriter out = new PrintWriter(new FileWriter(htmlLogPath, true))) {
                out.println("</table></body></html>");
            } catch (IOException e) {
                System.err.println("Failed to close HTML log file properly: " + e.getMessage());
            }
        }
    }

    /**
     * Execute a command.
     * @param command command string
     */
    private static void executeCommand(String command) {
        String[] parts = command.trim().split("\\s+");
        if (parts.length == 0) return;

        String cmd = parts[0].toLowerCase();
        try {
            switch (cmd) {
                case "n":
                    if (parts.length == 6) {
                        String name = parts[1];
                        double x = Double.parseDouble(parts[2]);
                        double y = Double.parseDouble(parts[3]);
                        double width = Double.parseDouble(parts[4]);
                        double height = Double.parseDouble(parts[5]);
                        graphicsManager.createRectangle(name, x, y, width, height);
                        System.out.printf("Created rectangle '%s' at (%.2f, %.2f) with width %.2f and height %.2f\n",
                                name, x, y, width, height);
                    } else {
                        System.out.println("Usage: n <name> <x> <y> <width> <height>");
                    }
                    break;
                case "line":
                    if (parts.length == 7) {
                        String name = parts[1];
                        double x1 = Double.parseDouble(parts[2]);
                        double y1 = Double.parseDouble(parts[3]);
                        double x2 = Double.parseDouble(parts[4]);
                        double y2 = Double.parseDouble(parts[5]);
                        graphicsManager.createLine(name, x1, y1, x2, y2);
                        System.out.printf("Created line '%s' from (%.2f, %.2f) to (%.2f, %.2f)\n",
                                name, x1, y1, x2, y2);
                    } else {
                        System.out.println("Usage: line <name> <x1> <y1> <x2> <y2>");
                    }
                    break;
                case "circle":
                    if (parts.length == 5) {
                        String name = parts[1];
                        double x = Double.parseDouble(parts[2]);
                        double y = Double.parseDouble(parts[3]);
                        double radius = Double.parseDouble(parts[4]);
                        graphicsManager.createCircle(name, x, y, radius);
                        System.out.printf("Created circle '%s' at (%.2f, %.2f) with radius %.2f\n",
                                name, x, y, radius);
                    } else {
                        System.out.println("Usage: circle <name> <x> <y> <radius>");
                    }
                    break;
                case "square":
                    if (parts.length == 5) {
                        String name = parts[1];
                        double x = Double.parseDouble(parts[2]);
                        double y = Double.parseDouble(parts[3]);
                        double length = Double.parseDouble(parts[4]);
                        graphicsManager.createSquare(name, x, y, length);
                        System.out.printf("Created square '%s' at (%.2f, %.2f) with length %.2f\n",
                                name, x, y, length);
                    } else {
                        System.out.println("Usage: square <name> <x> <y> <length>");
                    }
                    break;
                case "group":
                    if (parts.length >= 3) {
                        String groupName = parts[1];
                        List<String> memberNames = new ArrayList<>(Arrays.asList(parts).subList(2, parts.length));
                        graphicsManager.createGroup(groupName, memberNames);
                        System.out.printf("Created group '%s' with members: %s\n", groupName, String.join(", ", memberNames));
                    } else {
                        System.out.println("Usage: group <groupName> <member1> <member2> ...");
                    }
                    break;
                case "ungroup":
                    if (parts.length == 2) {
                        String name = parts[1];
                        graphicsManager.ungroup(name);
                        System.out.printf("Ungrouped '%s'\n", name);
                    } else {
                        System.out.println("Usage: ungroup <name>");
                    }
                    break;
                case "delete":
                    if (parts.length == 2) {
                        String name = parts[1];
                        graphicsManager.deleteShape(name);
                        System.out.printf("Deleted shape '%s'\n", name);
                    } else {
                        System.out.println("Usage: delete <name>");
                    }
                    break;
                case "boundingbox":
                    if (parts.length == 2) {
                        String name = parts[1];
                        BoundingBox box = graphicsManager.getBoundingBox(name);
                        if (box != null) {
                            System.out.printf("Bounding box for '%s': (%.2f, %.2f) to (%.2f, %.2f)\n",
                                    name, box.getMinX(), box.getMinY(), box.getMaxX(), box.getMaxY());
                        }
                    } else {
                        System.out.println("Usage: boundingbox <name>");
                    }
                    break;
                case "move":
                    if (parts.length == 4) {
                        String name = parts[1];
                        double dx = Double.parseDouble(parts[2]);
                        double dy = Double.parseDouble(parts[3]);
                        graphicsManager.moveShape(name, dx, dy);
                        System.out.printf("Moved shape '%s' by (%.2f, %.2f)\n", name, dx, dy);
                    } else {
                        System.out.println("Usage: move <name> <dx> <dy>");
                    }
                    break;
                case "shapeat":
                    if (parts.length == 3) {
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        String shapeName = graphicsManager.getTopShapeAt(x, y);
                        if (shapeName != null) {
                            System.out.printf("Top shape at (%.2f, %.2f): %s\n", x, y, shapeName);
                        } else {
                            System.out.printf("No shape at (%.2f, %.2f)\n", x, y);
                        }
                    } else {
                        System.out.println("Usage: shapeAt <x> <y>");
                    }
                    break;
                case "intersect":
                    if (parts.length == 3) {
                        String name1 = parts[1];
                        String name2 = parts[2];
                        boolean intersects = graphicsManager.doShapesIntersect(name1, name2);
                        System.out.printf("Shapes '%s' and '%s' %s intersect\n",
                                name1, name2, intersects ? "do" : "do not");
                    } else {
                        System.out.println("Usage: intersect <name1> <name2>");
                    }
                    break;
                case "list":
                    if (parts.length == 2) {
                        String name = parts[1];
                        String info = graphicsManager.getShapeInfo(name);
                        if (info != null) {
                            System.out.println(info);
                        }
                    } else {
                        System.out.println("Usage: list <name>");
                    }
                    break;
                case "listall":
                    List<String> allShapes = graphicsManager.getAllShapesInfo();
                    if (allShapes.isEmpty()) {
                        System.out.println("No shapes available.");
                    } else {
                        System.out.println("All shapes:");
                        for (String shape : allShapes) {
                            System.out.println("- " + shape);
                        }
                    }
                    break;
                default:
                    System.out.println("Unknown command: " + cmd);
                    System.out.println("Available commands: n, line, circle, square, group, ungroup, delete, boundingbox, move, shapeAt, intersect, list, listAll, quit");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }
}