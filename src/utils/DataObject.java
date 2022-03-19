package utils;

import java.util.*;

public class DataObject {

    Object object;

    public DataObject() {    }

    public DataObject(Object object) {
        this.object = object;
    }

    public int size() {
        if (isList()) {
            List list = (ArrayList) object;
            return list.size();
        }
        if (isMap()) {
            Map map = (Map) object;
            return map.size();
        }
        return 1;
    }

    /*public Map<String, String> getVariableToValueMap(List<String> variables) {

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < variables.size(); i++) {
            String value = getValueByVariable(variables.get(i));
            map.put(variables.get(i), value);
        }
        keyValueMap = map;
        return map;
    }*/

    public String getValueByVariableByPaths(String path) {
        String[] paths = path.split("[.]");
        Object childObject = object;
        for (int j = 1; j < paths.length; j++) {
            childObject = getChildObject(paths[j], childObject);
        }
        return childObject.toString();
    }
    public DataObject getDataObjectByVariable(List<String> variables) {
//        String[] paths = variables.split("[.]");
        Object childObject = object;
        for (int i = 1; i < variables.size(); i++) {
            childObject = getChildObject(variables.get(i), childObject);
        }
        return new DataObject(childObject);
    }

    public Object getChildObject(String path, Object childData) {
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
        return childData;
    }

    public DataObject get(int i) {
        if (object instanceof String) {
            return new DataObject(object.toString());
        }
        if (object instanceof Map) {
            Map map = (HashMap) object;
            return new DataObject(map);
        }
        if (object instanceof List) {
            List list = (ArrayList) object;
            return new DataObject(list.get(i));
        }
        return new DataObject();
    }

    public boolean isList(){
        if (object instanceof List) {
            return true;
        }
        return false;
    }
    public boolean isMap(){
        if (object instanceof Map) {
            return true;
        }
        return false;
    }
    public boolean isString(){
        if (object instanceof String) {
            return true;
        }
        return false;
    }
}
