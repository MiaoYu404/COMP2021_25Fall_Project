package clevis.system;

import java.io.File;

/**
 * class who in charge the IO
 */
public class IO {
    private File html;
    private File txt;

    /**
     * @param htmlAddress the address of html
     * @param txtAddress the address of txt
     */
    public IO(String htmlAddress, String txtAddress){
        this.html = new File(htmlAddress);
        this.txt = new File(txtAddress);
    }
}
