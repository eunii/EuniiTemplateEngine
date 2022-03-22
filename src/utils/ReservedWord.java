package utils;

public enum ReservedWord {

    BASIC("(\\<\\?)(.*?)(\\?\\>)", 2),
    NONE("", 0),
    ENTER("\\\\n", 0),
    ENTER_REPLACE("\r\n", 0),
    VARIABLE_SPLIT("\\.", 0),
    SPACE(" ", 0),
    SPACE_REPLACE("", 0),
    FOR_START("(\\<\\?)[- ]?(for)(.*?)(in)(.*?)(\\?\\>)", 5),
    FOR_END("(\\<\\?)[- ]?(endfor)[- ]?(\\?\\>)", 0),
    NULL_VALUE("?", 0),
    WILED_CARD("*", 0),
    VARIABLE("(\\<\\?)(\\=)(.*?)(\\?\\>)", 3);

    private final String pattern;
    private final int group;

    ReservedWord(String pattern, int group) {
        this.pattern = pattern;
        this.group = group;
    }

    public String getPattern() {
        return pattern;
    }

    public int getGroup() {
        return group;
    }
}
