package clevis.system;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class who in charge the IO
 */
public class Logger {
    private final File TXT;
    private final File HTML;

    /**
     * construct a logger with two address.
     * @param txtAddress        txt address
     * @param htmlAddress       html address
     */
    public Logger(String txtAddress, String htmlAddress) {
        TXT = new File(txtAddress);
        HTML = new File(htmlAddress);
    }

    /**
     * log method
     * @param content   content
     */
    public void log(String content) {
        // TODO: implement this method.
        if (content == null) throw new NullPointerException("content is null");
        writeHTML(content);
        writeTXT(content);
    }

    /**
     * write to txt file
     * @param content   content
     */
    public void writeTXT(String content) {
        // TODO: implement this method.
    }

    /**
     * write to html file
     * @param content   content
     */
    public void writeHTML(String content) {
        // TODO: implement this method.
    }
}
