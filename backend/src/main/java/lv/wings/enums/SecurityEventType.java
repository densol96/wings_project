package lv.wings.enums;

public enum SecurityEventType {

    LOGIN_SUCCESS("Veiksmīga pieteikšanās", "Successful login"), //
    LOGIN_FAILED("Neveiksmīga pieteikšanās", "Failed login"), //
    TOKEN_INVALID("Nederīgs tokens", "Invalid token"), //
    PASSWORD_CHANGED("Parole mainīta", "Password changed"), //
    AFTER_HOURS_ACCESS("Piekļuve ārpus darba laika", "After-hours access"), //
    ACCESS_FROM_NEW_IP("Piekļuve no jaunas IP adreses", "Access from new IP"), //
    UNUSUAL_USER_AGENT("Neparasts lietotāja aģents", "Unusual user agent");//

    private final String lv;
    private final String en;

    private SecurityEventType(String lv, String en) {
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
