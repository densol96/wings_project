package lv.wings.dto.request.payment;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.Country;

@Getter
@Setter
public class AddressDto {

    @NotBlank(message = "{street.required}")
    @Size(max = 100, message = "{street.size}")
    private String street;

    @NotBlank(message = "{houseNumber.required}")
    @Size(max = 10, message = "{houseNumber.size}")
    private String houseNumber;

    @Size(max = 10, message = "{apartment.size}")
    private String apartment;

    @NotBlank(message = "{city.required}")
    @Size(max = 50, message = "{city.size}")
    private String city;

    @NotBlank(message = "{postalCode.required}")
    @Pattern(
            regexp = "^(LV|LT|EE)-\\d{4,5}$",
            message = "{postalCode.invalid}")
    private String postalCode;

    @NotNull(message = "{country.required}")
    private Country country;

    @AssertTrue(message = "{postalCode.must-match}")
    public boolean isPostalAndCountryMatch() {
        if (country == null || postalCode == null)
            return false;
        return postalCode.toUpperCase().startsWith(country.name());
    }
}
