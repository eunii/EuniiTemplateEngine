package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String getStringByPath(String fileDir) throws IOException {
        String str = "";
        Path path = Paths.get(fileDir);
        str = Files.readString(path);
        return str;
    }

    public static List<String> getStringListByPath(String fileDir) throws IOException {
        List<String> fileLines = new ArrayList<>();
        Path path = Paths.get(fileDir);
        fileLines = Files.readAllLines(path);
        return fileLines;
    }

    public static void writeFileBy(List<String> results) {
        String rootPath = System.getProperty("user.dir");
        String fileName = "\\output.txt";
        try {
            FileWriter fw = new FileWriter(rootPath+fileName, true);
            for (int i = 0; i < results.size(); i++) {
                fw.write(results.get(i));
            }

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
