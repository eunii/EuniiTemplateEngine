package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataObject {

    private Object object;

    public DataObject() {
    }

    public DataObject(Object object) {
        this.object = object;
    }


    public DataObject getDataObjectByVariables(List<String> variables) {

        DataObject childDataObject = this;
        for (int i = 1; i < variables.size(); i++) {
            if (variables.get(i).equals(ReservedWord.WILED_CARD.getPattern())) {
                List<Object> list = new ArrayList<>();
                for (int j = 0; j < childDataObject.size(); j++) {
                    list.add(childDataObject.getDataObjectByVariable(String.valueOf(j)).getDataObjectByVariables(variables.subList(i, variables.size())).getObject());
                }
                return new DataObject(list);
            } else {
                childDataObject = childDataObject.getDataObjectByVariable(variables.get(i));
            }
        }
        return childDataObject;

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
        if (isString()) {
            return 1;
        }
        return 0;
    }

    private DataObject getDataObjectByVariable(String path) {
        if (isString()) {
            return new DataObject(getString());
        }
        if (isMap()) {
            return new DataObject(getMap().get(path));
        }
        if (isList()) {
            if (!getList().isEmpty()) {
                try {
                    return new DataObject(getList().get(Integer.parseInt(path)));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("리스트에 문자열을 넣을 수 없습니다." + path + "  " + getList().toString());
                }
            }
        }
        return new DataObject(ReservedWord.NULL_VALUE.getPattern());
    }

    private String getString() {
        if (!isString()) {
            throw new IllegalArgumentException("이 객체는 String 이 아닙니다." + object.toString());
        }
        return (String) object;
    }

    public List<Object> getList() {
        if (!isList()) {
            throw new IllegalArgumentException("이 객체는 List가 아닙니다." + object.toString());
        }
        return (List) object;
    }

    private Map<String, Object> getMap() {
        if (!isMap()) {
            throw new IllegalArgumentException("이 객체는 Map이 아닙니다." + object.toString());
        }
        return (Map) object;
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

    public Object getObject() {
        return this.object;
    }
}
