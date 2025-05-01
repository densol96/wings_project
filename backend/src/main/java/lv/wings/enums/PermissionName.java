package lv.wings.enums;

public enum PermissionName {

    MANAGE_PRODUCTS("Pārvaldīt produktus", "Manage products"), //
    MANAGE_ORDERS("Pārvaldīt pasūtījumus", "Manage orders"), //
    MANAGE_NEWS("Pārvaldīt ziņas", "Manage news"), //
    MANAGE_SECURITY("Pārvaldīt drošību", "Manage security"); //

    private final String lv;
    private final String en;

    private PermissionName(String lv, String en) {
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
