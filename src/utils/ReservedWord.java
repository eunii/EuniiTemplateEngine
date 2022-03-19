package utils;

public enum ReservedWord {

    VARIABLE("(\\<\\?\\=)(.*?)(\\?\\>)"), ENTER("\\\\n");

    private final String pattern;

    ReservedWord(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
