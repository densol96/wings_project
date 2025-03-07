package lv.wings.enums;

public enum SortBy {
    TITLE("title"), CREATEDAT("createdAt");

    private final String fieldName;

    SortBy(String fieldName) {
        this.fieldName = fieldName;
    }

    public static SortBy fromString(String value) throws IllegalArgumentException {
        return SortBy.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return this.fieldName;
    }
}
