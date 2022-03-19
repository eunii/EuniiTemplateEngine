package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class DataUtils {

    Object inputData;
    Map<String, String> keyValueMap;
    JSONParser parser = new JSONParser();

    public DataUtils(String stringData) {
        this.inputData = stringToObject(stringData);
    }

    public Object stringToObject(String jasonString) {
        try {
            return recursionParser(jasonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getVariableToValueMap(List<String> variables) {

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < variables.size(); i++) {
            String value = getValueByVariable(variables.get(i));
            map.put(variables.get(i), value);
        }
        keyValueMap = map;
        return map;
    }

    private Object recursionParser(String jasonString) throws ParseException {
        if (isLastData(jasonString)) {
            return jasonString;
        }
        Object obj =obj = parser.parse(jasonString.trim());
        if (isMapObject(jasonString)) {
            return getParentMap(obj);
        }
        if (isListObject(jasonString)) {
            return getParentList(obj);
        }
        return null;
    }

    private List<Object> getParentList(Object obj) throws ParseException {
        JSONArray jsonArray = (JSONArray) obj;
        ArrayList list = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            Object childObject = recursionParser(jsonArray.get(i).toString());
            list.add(childObject);
        }
        return list;
    }

    private Map<String, Object> getParentMap(Object obj) throws ParseException {
        JSONObject jsonObject = (JSONObject) obj;
        Iterator<String> keys = jsonObject.keySet().iterator();
        Map<String, Object> parentMap = new HashMap<>();
        while (keys.hasNext()) {
            String key = keys.next();
            Object object = jsonObject.get(key);
            Object childObject = recursionParser(object.toString());
            parentMap.put(key, childObject);
        }
        return parentMap;
    }

    private boolean isListObject(String jasonString) {
        return jasonString.startsWith("[");
    }

    private boolean isMapObject(String jasonString) {
        return jasonString.startsWith("{");
    }

    private boolean isLastData(String jasonString) {
        return !jasonString.startsWith("{") && !jasonString.startsWith("[");
    }

    private String getValueByVariable(String path) {
        String[] paths = path.split("[.]");
        Object childObject = inputData;
        for (int j = 1; j < paths.length; j++) {
            childObject = getChildObjectAfterCheckType(paths[j], childObject);
        }
        return childObject.toString();
    }

    public Object getChildObjectAfterCheckType(String path, Object childData) {
        if (childData instanceof String) {
            return childData.toString();
        }
        if (childData instanceof Map) {
            Map map = (HashMap) childData;
            return map.get(path);
        }
        if (childData instanceof List) {
            List list = (ArrayList) childData;
            return list.get(Integer.parseInt(path));
        }
        return null;
    }
}
