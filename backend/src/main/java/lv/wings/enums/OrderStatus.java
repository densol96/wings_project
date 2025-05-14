package lv.wings.enums;

public enum OrderStatus {
    IN_PROGRESS("Apstrādē", "In progress"), //
    PAID("Apmaksāts", "Paid"), //
    CANCELLED("Atcelts", "Cancelled"), //
    FAILED("Neizdevās", "Failed"), //
    SHIPPED("Nosūtīts", "Shipped"), //
    COMPLETED("Izpildīts", "Completed");

    private final String lv;
    private final String en;

    private OrderStatus(String lv, String en) {
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
