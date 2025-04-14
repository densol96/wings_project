package lv.wings.dto.request.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

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

        @Pattern(regexp = "^\\+?[0-9\\-\\s]{7,20}$", message = "{phone.invalid}")
        @Pattern(
                        regexp = "^(\\+371|\\+370|\\+372)\\s?\\d{6,10}$",
                        message = "{phone.invalid}")
        private String phoneNumber;

        @Valid
        private AddressDto address; // onyl required for courier delivery


}
