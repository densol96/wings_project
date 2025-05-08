package lv.wings.enums;

public enum AccountStatus {
    ACTIVE("Aktīvs", "Active"), //
    LOCKED("Bloķēts", "Locked"), //
    BANNED("Aizliegts", "Banned"); //

    private final String lv;
    private final String en;

    AccountStatus(String lv, String en) {
        this.lv = lv;
        this.en = en;
    }

    public String getLabel(String lang) {
        return switch (lang.toLowerCase()) {
            case "lv" -> lv;
            case "en" -> en;
            default -> lv;
        };
    }
}
