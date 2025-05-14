package lv.wings.dto.response.admin.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.Country;

@Getter
@Setter
@Builder
public class AddressAdminDto {
    private String street;
    private String houseNumber;
    private String apartment;
    private String city;
    private String postalCode;
    private Country country;
}
