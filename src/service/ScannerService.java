package service;

import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScannerService {

    private static Scanner scanner = new Scanner(System.in);

    public static String getDataFile() {
        boolean fail = true;
        System.out.println("데이터 파일 경로를 입력해주세요");
        String dataString = "";
        while (fail) {
            try {
                String filePath = scanner.nextLine();
                dataString = FileUtils.getStringByPath(filePath);
                fail = false;
            } catch (IOException e) {
                System.out.println("유효한 파일 경로가 아닙니다. 경로를 다시 입력하세요");
                fail = true;
            }
        }
        return dataString;
    }

    public static List<String> getTemplateFile() {
        boolean fail = true;
        List<String> jasonData = new ArrayList<>();
        System.out.println("탬플릿 파일 경로를 입력해주세요.");
        while (fail) {
            try {
                String filePath = scanner.nextLine();
                jasonData = FileUtils.getStringListByPath(filePath);
                fail = false;
            } catch (IOException e) {
                System.out.println("유효한 파일 경로가 아닙니다. 경로를 다시 입력하세요.");
                fail = true;
            }
        }
        return jasonData;
    }
}
