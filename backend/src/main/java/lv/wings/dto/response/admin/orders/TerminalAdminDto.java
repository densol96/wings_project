package lv.wings.dto.response.admin.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.Country;

@Getter
@Setter
@Builder
public class TerminalAdminDto {
    private Integer id;
    private String zip;
    private String name;
    private Country country;
    private String address;
}
