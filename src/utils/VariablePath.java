package utils;

import java.util.Arrays;
import java.util.List;

public class VariablePath {

    private String pathOrigin;
    private List<String> paths;


    public VariablePath(String pathOrigin) {
        this.pathOrigin = pathOrigin;
    }

    public List<String> getPaths() {
        pathOrigin = pathOrigin.replaceAll(ReservedWord.SPACE.getPattern(), ReservedWord.SPACE_REPLACE.getPattern());
        return Arrays.asList(pathOrigin.split(ReservedWord.VARIABLE_SPLIT.getPattern()));
    }
}
