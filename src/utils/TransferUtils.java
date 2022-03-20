package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransferUtils {

    TemplateReader templateReader;
    DataReader dataReader;
    DataObject rootDataObject;

    Pattern basicPattern = Pattern.compile(ReservedWord.BASIC.getPattern());
    Pattern variablePattern = Pattern.compile(ReservedWord.VARIABLE.getPattern());
    Pattern forStartPattern = Pattern.compile(ReservedWord.FOR_START.getPattern());

    List<String> templateLines;


    public TransferUtils(TemplateReader templateReader, DataReader dataReader) {
        this.templateReader = templateReader;
        this.dataReader = dataReader;
        this.templateLines = templateReader.getTemplateLines();
        this.rootDataObject = dataReader.getDataObject();
    }

    public List<String> transfer() {

        List<String> result = new ArrayList<>();
        List<String> forTemplate = new ArrayList<>();
        DataObject forDataObject = new DataObject();

        boolean isForStart = false;
        //탬플릿 한줄한줄 읽기
        for (int i = 0; i < templateLines.size(); i++) {
            String line = templateLines.get(i);
            ReservedWord type = getLineType(line);
            if (!isForStart) {
                if (type.equals(ReservedWord.NONE)) {
                    result.add(line);
                }
                if (type.equals(ReservedWord.VARIABLE)) {
                    result.add(transferTemplateToResultWithTemplateAndData(line, rootDataObject));
                }
                if (type.equals(ReservedWord.FOR_START)) {
                    isForStart = true;
                    forDataObject = getForDataObject(line, rootDataObject);
                }
            } else {
                if (type.equals(ReservedWord.FOR_END)) {
                    result.addAll(translateForTemplateToResultWithTemplateAndData(forTemplate, forDataObject));
                    isForStart = false;
                } else {
                    forTemplate.add(line);
                }
            }
        }
        return result;
    }

    public List<String> translateForTemplateToResultWithTemplateAndData(List<String> lines, DataObject dataObject) {
        List<String> result = new ArrayList<>();
        List<String> forTemplate = new ArrayList<>();
        DataObject forDataObject = dataObject;

        for (int j = 0; j < dataObject.size(); j++) {
            boolean isForStart = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                ReservedWord type = getLineType(line);
                if (!isForStart) {
                    if (type.equals(ReservedWord.NONE)) {
                        result.add(line);
                    }
                    if (type.equals(ReservedWord.VARIABLE)) {
                        result.add(transferTemplateToResultWithTemplateAndData(line, new DataObject(dataObject.getList().get(j))));
                    }
                    if (type.equals(ReservedWord.FOR_START)) {
                        isForStart = true;
                        forDataObject = getForDataObject(line, dataObject);
                    }
                } else {
                    if (type.equals(ReservedWord.FOR_END)) {
                        result.addAll(translateForTemplateToResultWithTemplateAndData(forTemplate, forDataObject));
                        isForStart = false;
                    } else {
                        forTemplate.add(line);
                    }
                }
            }
        }
        return result;
    }

    private DataObject getForDataObject(String line, DataObject dataObject) {
        Matcher matcher = forStartPattern.matcher(line);
        DataObject childDataObject = dataObject;
        while (matcher.find()) {
            String variable = matcher.group(5);
            variable = variable.replaceAll(" ", "");
            List<String> variables = Arrays.asList(variable.split("\\."));
            childDataObject = dataObject.getDataObjectByVariable(variables);
        }

        return childDataObject;
    }

    private String transferTemplateToResultWithTemplateAndData(String line, DataObject dataObject) {
        Matcher matcher = variablePattern.matcher(line);
        while (matcher.find()) {
            String variable = matcher.group(3);
            variable = variable.replaceAll(" ", "");
            List<String> variables = Arrays.asList(variable.split("\\."));
            DataObject value = dataObject.getDataObjectByVariable(variables);
            line = line.replace(matcher.group(), value.getString());
        }
        return line;
    }

    public ReservedWord getLineType(String templateLine) {
        Matcher matcher = basicPattern.matcher(templateLine);
        while (matcher.find()) {
            String reservedWord = matcher.group();
            //// 변수 명이면. 라인 치환
            reservedWord = reservedWord.replaceAll(" ", "");
            if (reservedWord.matches(ReservedWord.VARIABLE.getPattern())) {
                return ReservedWord.VARIABLE;
            }
            if (reservedWord.matches(ReservedWord.FOR_START.getPattern())) {
                return ReservedWord.FOR_START;
            }
            if (reservedWord.matches(ReservedWord.FOR_END.getPattern())) {
                return ReservedWord.FOR_END;
            }
        }
        return ReservedWord.NONE;
    }

    public void writeFile(List<String> result) {

        FileUtils.writeFileBy2(result);
    }
}
