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
            if (variables.get(i).equals("*")) {
                List<Object> list = new ArrayList<>();
                for (int j = 0; j < childDataObject.size(); j++) {
                    list.add(childDataObject.getChildObject(j + "", childDataObject).getDataObjectByVariable(variables.subList(1, variables.size())).getObject());
                }
                return new DataObject(list);
            } else {
                childDataObject = childDataObject.getChildObject(variables.get(i), childDataObject);
            }
        }
        return childDataObject;
    }


    private DataObject getChildObject(String path, DataObject dataObject) {
        System.out.println(dataObject.toString());
        if (isString()) {
            return new DataObject(dataObject.getString());
        }
        if (isMap()) {
            return new DataObject(dataObject.getMap().get(path));
        }
        if (isList()) {
            if(!dataObject.getList().isEmpty()){
                return new DataObject(dataObject.getList().get(Integer.parseInt(path)));
            }
        }
        return new DataObject();
    }

    public List<Object> getList() {
        return (List) object;
    }

    private Map<String, Object> getMap() {
        return (Map) object;
    }

    public String getString() {
        System.out.println("===========");
        Object aaa = object;
        System.out.println("------------");
        return (String) object;
    }

/*    public DataObject get(int i) {
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
    }*/

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
        return object;
    }
}
