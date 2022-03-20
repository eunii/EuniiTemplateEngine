package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String readFileToString(String path) {
        String template = readFileByPath(path);
        return template;
    }

    public static String readFileByPath(String fileDir) {
        String str = "";
        try {
            Path path = Paths.get(fileDir);
            str = Files.readString(path);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static List<String> readFileByLine(String fileDir) {
        List<String> fileLines = new ArrayList<>();
        try {
            Path path = Paths.get(fileDir);
            fileLines = Files.readAllLines(path);
            return fileLines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }

    public static void writeFileBy(String string) {
        Path path = Paths.get("D:\\study\\kakaobank\\study_project\\output.txt");
        try {
            Files.writeString(path,
                    string, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void writeFileBy2(List<String> results) {
        String filePath = "D:\\study\\kakaobank\\study_project\\output.txt";

        try {
            FileWriter fw = new FileWriter(filePath, true);
            for (int i = 0; i < results.size(); i++) {
                fw.write(results.get(i).replaceAll(ReservedWord.ENTER.getPattern(), "\r\n"));
            }

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
