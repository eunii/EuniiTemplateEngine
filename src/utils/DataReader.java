package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class DataReader {

    Object inputObject;
    JSONParser parser = new JSONParser();
    FileUtils fileUtils = new FileUtils();

    public DataReader(String dataFilePath) {
        String data = fileUtils.readFileToString(dataFilePath);
        this.inputObject = stringToObject(data);
    }

    public Object stringToObject(String jasonString) {
        try {
            return recursionParser(jasonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DataObject getDataObject() {
        return new DataObject(inputObject);
    }

    private Object recursionParser(String jasonString) throws ParseException {
        if (isLastData(jasonString)) {
            return jasonString;
        }
        Object object = parser.parse(jasonString.trim());
        if (isMapObject(jasonString)) {
            return getParentMap(object);
        }
        if (isListObject(jasonString)) {
            return getParentList(object);
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
}
