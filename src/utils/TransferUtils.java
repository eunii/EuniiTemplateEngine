package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransferUtils {


    DataObject rootDataObject;
    List<String> templateList;

    Pattern basicPattern = Pattern.compile(ReservedWord.BASIC.getPattern());
    Pattern variablePattern = Pattern.compile(ReservedWord.VARIABLE.getPattern());
    Pattern forStartPattern = Pattern.compile(ReservedWord.FOR_START.getPattern());

    public TransferUtils(DataObject dataObject, List<String> templateList) {
        this.rootDataObject = dataObject;
        this.templateList = templateList;
    }

    public List<String> transfer() {

        List<String> result = new ArrayList<>();
        List<String> forTemplate = new ArrayList<>();
        DataObject forDataObject = new DataObject();

        for (int i = 0; i < templateList.size(); i++) {
            String line = templateList.get(i).replaceAll(ReservedWord.ENTER.getPattern(), ReservedWord.ENTER_REPLACE.getPattern());
            ReservedWord type = getLineType(line);
            if (forTemplate.isEmpty()) {
                if (type.equals(ReservedWord.NONE)) {
                    result.add(line);
                }
                if (type.equals(ReservedWord.VARIABLE)) {
                    result.add(transferTemplateToResultWithTemplateAndData(line, rootDataObject));
                }
                if (type.equals(ReservedWord.FOR_START)) {
                    forTemplate.add(line);
                }
            } else {
                if (type.equals(ReservedWord.FOR_END)) {
                    forDataObject = getForDataObject(forTemplate.get(0), rootDataObject);
                    for (int k = 0; k < forDataObject.getList().size(); k++) {
                        for (int j = 1; j < forTemplate.size(); j++) {
                            result.add(transferTemplateToResultWithTemplateAndData(forTemplate.get(j), new DataObject(forDataObject.getList().get(k))));
                        }
                    }
                    forTemplate.clear();
                } else {
                    forTemplate.add(line);
                }
            }
        }
        return result;
    }

    private String transferTemplateToResultWithTemplateAndData(String line, DataObject dataObject) {
        Matcher matcher = variablePattern.matcher(line);
        while (matcher.find()) {
            String variable = matcher.group(ReservedWord.VARIABLE.getGroup());
            VariablePath variables = new VariablePath(variable);
            DataObject value = dataObject.getDataObjectByVariables(variables.getPaths());
            if (value.size() == 0){
                return "";
            }
            line = line.replace(matcher.group(), String.valueOf(value.getObject()));
        }
        return line;
    }

    private DataObject getForDataObject(String line, DataObject dataObject) {
        Matcher matcher = forStartPattern.matcher(line);
        DataObject childDataObject = dataObject;
        while (matcher.find()) {
            String variable = matcher.group(ReservedWord.FOR_START.getGroup());
            VariablePath variables = new VariablePath(variable);
            childDataObject = dataObject.getDataObjectByVariables(variables.getPaths());
        }
        return childDataObject;
    }

    private ReservedWord getLineType(String templateLine) {
        Matcher matcher = basicPattern.matcher(templateLine);
        while (matcher.find()) {
            String reservedWord = matcher.group();
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
