package support;

import org.junit.Ignore;

import java.io.*;

/**
 * Created by Administrator on 2017/4/11.
 */
@Ignore
public class WriteAndReadFile {
    public static void writeFile(String filePath, String sets)
            throws IOException {
        String basePath = System.getProperty("user.dir") + File.separator + "jsondir" + File.separator;
        FileWriter fw = new FileWriter(basePath + filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(sets);
        out.println();
        fw.close();
        out.close();
    }

    public static String readFile(String path) {
        String basePath = System.getProperty("user.dir") + File.separator + "jsondir" + File.separator;
        File file = new File(basePath + path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr;
    }
}
