package lv.wings.dto.response.terminal;

import lombok.Builder;
import lombok.Data;
import lv.wings.enums.Country;

@Data
@Builder
public class TerminalDto {
    private Integer id;
    private String name;
    private String address;
    private Country country;
}
