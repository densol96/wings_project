package lv.wings.dto.response.admin.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderAdminDto {
    private Integer id;
    private StatusDto status;
    private BigDecimal total;
    private CustomerInfoDto customer;
    private DeliveryMethodDto delivery;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private UserMinDto lastModifiedBy;
}
