package lv.wings.dto.response.admin.orders;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerFullAdminDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AddressAdminDto address;
    private String additionalDetails;
}
