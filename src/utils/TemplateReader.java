package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateReader {

    private String templateOrigin;
    private String templateResult;
    public List<String> templateLines;
    private FileUtils fileUtils = new FileUtils();

    public TemplateReader(String templateFilePath) {
        String template = fileUtils.readFileToString(templateFilePath);
        this.templateOrigin = template;
        this.templateResult = template;
        this.templateLines = fileUtils.readFileByLine(templateFilePath);

    }

    public List<String> getVariable() {
        Pattern pattern = Pattern.compile(ReservedWord.VARIABLE.getPattern());
        Matcher matcher = pattern.matcher(templateOrigin);
        List<String> lists = new ArrayList<>();
        while (matcher.find()) {
            String variable = matcher.group(2);
            variable = variable.replaceAll(" ", "");
            lists.add(variable);
        }
        return lists;
    }



    public String templateToResult(Map<String, String> keyValueMap) {
        replaceValue(keyValueMap);
        replaceEnter();
        return templateResult;
    }

    private void replaceValue(Map<String, String> keyValueMap) {
        Pattern pattern = Pattern.compile(ReservedWord.VARIABLE.getPattern());
        Matcher matcher = pattern.matcher(templateResult);
        while (matcher.find()) {
            String variable = matcher.group(2);
            variable = variable.replaceAll(" ", "");
            keyValueMap.get(variable);
            templateResult = templateResult.replace(matcher.group(), keyValueMap.get(variable));
        }
    }

    private String replaceEnter() {
        Pattern pattern = Pattern.compile(ReservedWord.ENTER.getPattern());
        Matcher matcher = pattern.matcher(templateResult);
        while (matcher.find()) {
            templateResult = templateResult.replace(matcher.group(), "");
        }
        return templateResult;
    }

    public List<String> getTemplateLines() {
        return templateLines;
    }
}
