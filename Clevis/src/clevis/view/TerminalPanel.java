package clevis.view;

import clevis.system.Console;
import clevis.system.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Terminal Panel
 */
public class TerminalPanel extends JPanel {
    /**
     * Console Font Size
     */
    public static final int CONSOLE_FONT_SIZE = 14;
    /**
     * Background Color
     */
    public static final Color BACKGROUND = new Color(30, 30, 30);
    /**
     * Foreground Color
     */
    public static final Color FOREGROUND = new Color(200, 255, 200);
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final Console console;
    private final ClevisUI parent;
    private final Logger logger;

    /**
     * Construction
     * @param console       console
     * @param mainUI        parent Panel
     */
    public TerminalPanel(Console console, ClevisUI mainUI) {
        this.console = console;
        this.parent = mainUI;
        this.logger = console.logger();

        setLayout(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, CONSOLE_FONT_SIZE));
        outputArea.setBackground(BACKGROUND);
        outputArea.setForeground(FOREGROUND);

        // 重定向 System.out
        PrintStream ps = new PrintStream(new TextAreaOutputStream(outputArea));
        System.setOut(ps);
        System.setErr(ps);

        JScrollPane scroll = new JScrollPane(outputArea);
        inputField = new JTextField();
        inputField.setFont(new Font("Consolas", Font.PLAIN, CONSOLE_FONT_SIZE));
        inputField.addActionListener(e -> processCommand());

        add(scroll, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        appendLine("=== Clevis is launched ===");
        appendLine("Enter the operation and press 'enter' to run. Press 'ESC' anytime to quit.");
        appendLine("");
    }

    private void processCommand() {
        String cmd = inputField.getText().trim();
        if (cmd.isEmpty()) {
            inputField.setText("");
            return;
        }

        appendLine("> " + cmd);
        inputField.setText("");

        logger.log(cmd);
        console.readOperation(cmd);

        parent.refreshCanvas();
    }

    private void appendLine(String line) {
        outputArea.append(line + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    // 把 System.out 重定向到 JTextArea
    private static class TextAreaOutputStream extends OutputStream {
        private final JTextArea textArea;
        private final StringBuilder sb = new StringBuilder();

        TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            if (b == '\r') return;
            if (b == '\n') {
                String text = sb.toString() + "\n";
                SwingUtilities.invokeLater(() -> textArea.append(text));
                sb.setLength(0);
            } else {
                sb.append((char) b);
            }
        }
    }
}