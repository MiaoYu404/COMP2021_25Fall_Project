package hk.edu.polyu.comp.comp2021.clevis.controller;

import java.io.*;

/**
 * class who in charge the IO
 */
public class Logger {
    private File TXT;
    private File HTML;

    private PrintWriter txtWriter;
    private PrintWriter htmlWriter;

    private int opIndex = 0;

    /**
     * construct a logger with two address.
     * @param txtAddress        txt address
     * @param htmlAddress       html address
     */
    public Logger(String htmlAddress, String txtAddress) {
        if (htmlAddress != null) {
            HTML = new File(htmlAddress);
            try {
                htmlWriter = new PrintWriter(new BufferedWriter(new FileWriter(HTML, false)));
                writeHtmlHeader();
            } catch (IOException e) {
                throw new UncheckedIOException("Cannot create log html files", e);
            }
        }

        if (txtAddress != null) {
            TXT = new File(txtAddress);
            try {
                txtWriter = new PrintWriter(new BufferedWriter(new FileWriter(TXT, false)));
            } catch (IOException e) {
                throw new UncheckedIOException("Cannot create log txt files", e);
            }
        }
    }

    private void writeHtmlHeader() {
        htmlWriter.println("<!DOCTYPE html>");
        htmlWriter.println("<html><head><meta charset=\"UTF-8\">");
        htmlWriter.println("<title>Clevis command log</title>");
        htmlWriter.println("<style>table{border-collapse:collapse;} td,th{border:1px solid #999;padding:4px 8px;}</style>");
        htmlWriter.println("</head><body>");
        htmlWriter.println("<table>");
        htmlWriter.println("<tr><th>#</th><th>Command</th></tr>");
    }

    private void writeHtmlFooter() {
        htmlWriter.println("</table></body></html>");
    }

    /**
     * log method
     * @param content   content
     */
    public void log(String content) {
        // TODO: implement this method.
        if (content == null) throw new NullPointerException("content is null");

        ++opIndex;
        writeHTML(content);
        writeTXT(content);
    }

    /**
     * write to txt file
     * @param content   content
     */
    public void writeTXT(String content) {
        txtWriter.printf("%s%n", content);
        txtWriter.flush();
    }

    /**
     * write to html file
     * @param content   content
     */
    public void writeHTML(String content) {
        String escaped = content
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");

        htmlWriter.printf("  <tr><td>%d</td><td>%s</td></tr>%n", opIndex, escaped);
        htmlWriter.flush();
    }

    /**
     * close the files cleanly.
     */
    public void close() {
        writeHtmlFooter();
        if (txtWriter != null) { txtWriter.close(); }
        if (htmlWriter != null) { htmlWriter.close(); }
    }
}
