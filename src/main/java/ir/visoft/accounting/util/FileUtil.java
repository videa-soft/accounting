package ir.visoft.accounting.util;

import java.io.IOException;

/**
 * @author Amir
 */
public class FileUtil {

    public static void openFile(String fileName) throws IOException {
        Runtime.getRuntime().exec("cmd /c start " + fileName);
    }
}
