package lv.wings.dto.request.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.Country;

@Getter
@Setter
public class CustomerDataDto {

        @NotBlank(message = "{firstName.required}")
        @Pattern(
                        regexp = "^[A-Za-zĀ-ž\\-\\s]{2,50}$",
                        message = "{firstName.invalid}")
        private String firstName;

        @NotBlank(message = "{lastName.required}")
        @Pattern(
                        regexp = "^[A-Za-zĀ-ž\\-\\s]{2,50}$",
                        message = "{lastName.invalid}")
        private String lastName;

        @Email(message = "{email.invalid}")
        @NotBlank(message = "{email.required}")
        private String email;

        @Pattern(regexp = "^\\+37[0-2]\\d{8}$", message = "{phone.invalid}")
        @NotBlank(message = "{phone.required}")
        private String phoneNumber;

        @Valid
        private AddressDto address; // onyl required for courier delivery

        @Size(max = 500, message = "{additionDetails.size}")
        private String additionDetails;

        @AssertTrue(message = "{phone.must-match}")
        public boolean isPhoneAndCountryMatch() {
                if (phoneNumber != null && address != null && address.getCountry() != null) {
                        if (address.getCountry() == Country.LV && !phoneNumber.startsWith("+371")
                                        || address.getCountry() == Country.LT && !phoneNumber.startsWith("+370")
                                        || address.getCountry() == Country.EE && !phoneNumber.startsWith("+372")) {
                                return false;
                        }
                }
                return true;
        }
}
