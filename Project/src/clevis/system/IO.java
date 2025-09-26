package clevis.system;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class who in charge the IO
 */
public class IO {
    private String html;
    private File txt;
    private ArrayList<String> htmlTable;
    private ArrayList<String> txtOrder;

    /**
     * @param htmlAddress the address of html
     * @param txtAddress the address of txt
     */
    public IO(String htmlAddress, String txtAddress){
        this.html = htmlAddress;
        this.txt = new File(txtAddress);
        this.htmlTable = new ArrayList<>();
        this.txtOrder = new ArrayList<>();
    }


    /* TODO: 要求说只能用standard Java SE Development Kit 21 API，所以读取html时用的是笨办法
        再加上对这一块内容并不熟悉，代码有些累赘
     */
    /**
     * read the html
     * @throws Exception empty file
     */
    void readHtml() throws Exception {
        try {
            String htmlContent = readHtmlFile(html);
            List<List<String>> tableData = extractTableData(htmlContent);

            // 打印提取到的数据
            for (List<String> row : tableData) {
                htmlTable.add(String.valueOf(row));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String readHtmlFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        }
        return contentBuilder.toString();
    }


    private static List<List<String>> extractTableData(String htmlContent) {
        List<List<String>> tableData = new ArrayList<>();

        // 使用正则表达式找到<table>...</table>块
        // 注意：这是一个非常简化d正则表达式，实际应用中需要更健壮的解析
        Pattern tablePattern = Pattern.compile("<table.*?>(.*?)</table>", Pattern.DOTALL);
        Matcher tableMatcher = tablePattern.matcher(htmlContent);

        if (tableMatcher.find()) {
            String tableHtml = tableMatcher.group(1); // 提取<table>标签内部的内容

            // 使用正则表达式找到<tr>...</tr>行
            Pattern rowPattern = Pattern.compile("<tr.*?>(.*?)</tr>", Pattern.DOTALL);
            Matcher rowMatcher = rowPattern.matcher(tableHtml);

            while (rowMatcher.find()) {
                String rowHtml = rowMatcher.group(1);
                List<String> rowData = new ArrayList<>();

                // 使用正则表达式找到<td>...</td>单元格
                Pattern cellPattern = Pattern.compile("<td.*?>(.*?)</td>", Pattern.DOTALL);
                Matcher cellMatcher = cellPattern.matcher(rowHtml);

                while (cellMatcher.find()) {
                    // 提取单元格文本，并去除HTML标签（非常基础的去除）
                    String cellText = cellMatcher.group(1).replaceAll("<.*?>", "").trim();
                    rowData.add(cellText);
                }
                tableData.add(rowData);
            }
        }
        return tableData;
    }

    /**
     * read the txt
     * @throws Exception NoEmptyFile
     */
    void readTxt() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(txt))) {
            String line;
            while ((line = reader.readLine()) != null) {
                txtOrder.add(line);
            }
        }
    }

    /**
     * @return get the html content
     */
    public ArrayList<String> getHtmlTable() {return htmlTable;}

    /**
     * @return get the txt content
     */
    public ArrayList<String> getTxtOrder() {return txtOrder;}


    /**
     * @param args for test
     * @throws Exception for empty file
     */
    public static void main(String[] args) throws Exception {
        // TODO: Qusetion for the address input
//        IO io = new IO("Project/clevis.iml",
//                "-txt d:\\log.txt");
        IO io = new IO("Project/test/test reading.html", "Project/test/test.txt");
        io.readHtml();
        io.readTxt();
        System.out.println(io.getHtmlTable());
        System.out.println(io.getTxtOrder());
    }

}
