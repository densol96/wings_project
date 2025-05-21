package lv.wings.enums;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum LocaleCode {
    EN("en"), LV("lv");

    private final String code;

    LocaleCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LocaleCode from(String code) {
        return Arrays.stream(values())
                .filter(locale -> locale.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to LocaleCode.from due to unknown locale: " + code));
    }
}
