package lv.wings.enums;

public enum SortBy {
    TITLE, CREATEDAT;

    public static SortBy fromString(String value) throws IllegalArgumentException {
        return SortBy.valueOf(value.toUpperCase());
    }
}
