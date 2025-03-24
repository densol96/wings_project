package lv.wings.enums;

public enum LocaleCode {
    EN("en"),
    LV("lv");

    private final String code;

    LocaleCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}