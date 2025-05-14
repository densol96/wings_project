package lv.wings.dto.response.admin.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.DeliveryMethod;

@Getter
@Setter
@Builder
public class DeliveryMethodDto {
    private DeliveryMethod methodCode;
    private String methodName;
    private String fullNameAddress;
}
