package utils;

import java.util.*;

public class DataObject {

    private Object object;

    public DataObject() {
    }

    public DataObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
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

    public DataObject getDataObjectByVariable(List<String> variables) {
        DataObject childDataObject = this;
        for (int i = 1; i < variables.size(); i++) {
            childDataObject = childDataObject.getChildObject(variables.get(i));
        }
        return childDataObject;
    }

    private DataObject getChildObject(String path) {
        if (isString()) {
            return new DataObject(this.getString());
        }
        if (isMap()) {
            return new DataObject(this.getMap().get(path));
        }
        if (isList()) {
            return new DataObject(this.getList().get(Integer.parseInt(path)));
        }
        return new DataObject();
    }

    private List<Object> getList() {
        return (List) this.object;
    }

    private Map<String, Object> getMap() {
        return (Map) this.object;
    }

    public String getString() {
        return (String) this.object;
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

    private boolean isList() {
        if (object instanceof List) {
            return true;
        }
        return false;
    }

    private boolean isMap() {
        if (object instanceof Map) {
            return true;
        }
        return false;
    }

    private boolean isString() {
        if (object instanceof String) {
            return true;
        }
        return false;
    }
}
