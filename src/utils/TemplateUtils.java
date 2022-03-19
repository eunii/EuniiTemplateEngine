package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtils {

    private String templateOrigin;
    private String templateResult;

    public TemplateUtils(String templateOrigin) {
        this.templateOrigin = templateOrigin;
        this.templateResult = templateOrigin;
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

    public List<String> getBasicPattern() {
        Pattern pattern = Pattern.compile(ReservedWord.BASIC.getPattern());
        Matcher matcher = pattern.matcher(templateOrigin);
        List<String> lists = new ArrayList<>();
        while (matcher.find()) {
            String variable = matcher.group(2);
            variable = variable.replaceAll(" ", "");
            lists.add(variable);
            //=로 시작하는 변수 값일때
            if(variable.startsWith(ReservedWord.VARIABLE.getPattern())){

            }

            if(variable.matches(ReservedWord.FOR_START.getPattern())){
                //for문 시작하는값일때

                //
            }

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
}
