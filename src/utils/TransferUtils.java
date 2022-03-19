package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransferUtils {

    List<String> templateList;
    List<String> resultList = new ArrayList<>();
    DataToObjectUtils dataToObjectUtils;
    Pattern bagicPattern = Pattern.compile(ReservedWord.BASIC.getPattern());
    Pattern variablePattern = Pattern.compile(ReservedWord.VARIABLE.getPattern());
    Pattern forStartPattern = Pattern.compile(ReservedWord.FOR_START.getPattern());
    Pattern forEndPattern = Pattern.compile(ReservedWord.FOR_END.getPattern());
    DataObject dataObject;

    public TransferUtils(List<String> templateList, DataToObjectUtils dataToObjectUtils) {
        this.templateList = templateList;
        this.dataObject = dataToObjectUtils.getDataObject();
    }

    public void setTemplateList(List<String> templateList) {
        this.templateList = templateList;
    }

    public List<String> start(List<String> lines, DataObject dataObject) {
        List<String> parentTemplate = new ArrayList<>();
        List<String> childTemplate = new ArrayList<>();
        DataObject childDataObject = null;

            boolean isForStart = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                ReservedWord type = getLineType(line);
                if (!isForStart) {
                    if (type.equals(ReservedWord.NONE)) {
                        parentTemplate.add(line);
                    }
                    if (type.equals(ReservedWord.VARIABLE)) {
                        String line2 =transferVariable(line, dataObject);
                        parentTemplate.add(line2);
                    }
                    if (type.equals(ReservedWord.FOR_START)) {
                        isForStart = true;
                        childDataObject = setChildDataObject(line, dataObject);
                    }
                } else {
                    if (type.equals(ReservedWord.FOR_END)) {
                        List<String> line3s = startFor(childTemplate, childDataObject, childDataObject.size());
                        parentTemplate.addAll(line3s);
                        isForStart = false;
                    } else {
                        childTemplate.add(line);
                    }
                }

        }
        return parentTemplate;
    }
    public List<String> startFor(List<String> lines, DataObject dataObject, int repeatSize) {
        List<String> parentTemplate = new ArrayList<>();
        List<String> childTemplate = new ArrayList<>();
        DataObject childDataObject = null;

        for (int j = 0; j < repeatSize; j++) {
            boolean isForStart = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                ReservedWord type = getLineType(line);
                if (!isForStart) {
                    if (type.equals(ReservedWord.NONE)) {
                        parentTemplate.add(line);
                    }
                    if (type.equals(ReservedWord.VARIABLE)) {
                        String line2 =transferVariable(line, dataObject.get(j));
                        parentTemplate.add(line2);
                    }
                    if (type.equals(ReservedWord.FOR_START)) {
                        isForStart = true;
                        childDataObject = setChildDataObject(line, dataObject);
                    }
                } else {
                    if (type.equals(ReservedWord.FOR_END)) {
                        List<String> line3s = startFor(childTemplate, childDataObject, childDataObject.size());
                        parentTemplate.addAll(line3s);
                        isForStart = false;
                    } else {
                        childTemplate.add(line);
                    }
                }
            }

        }
        return parentTemplate;
    }

    private DataObject setChildDataObject(String line, DataObject dataObject) {
        Matcher matcher = forStartPattern.matcher(line);
        DataObject childDataObject = new DataObject();
        while (matcher.find()) {
            String variable = matcher.group(5);
            variable = variable.replaceAll(" ", "");
//            childDataObject = dataObject.getDataObjectByVariable(variable+"."+size);
            List<String> variables = Arrays.asList(variable.split("\\."));
            childDataObject = dataObject.getDataObjectByVariable(variables);
        }
        return childDataObject;
    }

    private String transferVariable(String line, DataObject dataObject) {
        Matcher matcher = variablePattern.matcher(line);
        while (matcher.find()) {
            String variable = matcher.group(3);
            variable = variable.replaceAll(" ", "");
            String value = dataObject.getValueByVariableByPaths(variable);
            line = line.replace(matcher.group(), value);
        }
        return line;
    }
    private String transferVariable(String line, DataObject dataObject, int j) {
        Matcher matcher = variablePattern.matcher(line);
        dataObject.get(j);
            while (matcher.find()) {
                String variable = matcher.group(2);
                variable = variable.replaceAll(" ", "");
                String value = dataObject.getValueByVariableByPaths(variable);
                line = line.replace(matcher.group(), value);
            }
        return line;
    }

    //        //탬플릿에서 변수들 리스트 찾아온다
    public ReservedWord getLineType(String templateLine) {

        Matcher matcher = bagicPattern.matcher(templateLine);
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
}
