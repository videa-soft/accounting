package ir.visoft.accounting.util;

import java.io.File;
import java.io.IOException;

/**
 * @author Amir
 */
public class FileUtil {

    public static void createDirectory(String directoryName) {
        File theFile = new File(directoryName);
        //noinspection ResultOfMethodCallIgnored
        theFile.mkdirs();
    }

    public static void openFile(String fileName) throws IOException {
        Runtime.getRuntime().exec("cmd /c start " + fileName);
    }
}
