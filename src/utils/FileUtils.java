package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public String readFileToString(String path) {
        String template = readFileByPath(path);
        return template;
    }

    public String readFileByPath(String fileDir) {
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

    public void writeFileBy(String string) {
        Path path = Paths.get("D:\\study\\kakaobank\\study_project\\output.txt");
        try {
            Files.writeString(path,
                    string, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
