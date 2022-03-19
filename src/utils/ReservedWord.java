package utils;

public enum ReservedWord {
    BASIC("(\\<\\?)(.*?)(\\?\\>)"),
    NONE(""),
    ENTER("\\\\n"),
    FOR_START("(\\<\\?)[- ]?(for)(.*?)(in)(.*?)(\\?\\>)"),
    FOR_END("(\\<\\?)[- ]?(endfor)[- ]?(\\?\\>)"),
    VARIABLE("(\\<\\?)(\\=)(.*?)(\\?\\>)");


    private final String pattern;

    ReservedWord(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
