package lv.wings.mapper;

import org.mapstruct.Mapper;
import lv.wings.dto.request.payment.CustomerDataDto;
import lv.wings.dto.response.admin.orders.CustomerFullAdminDto;
import lv.wings.dto.response.admin.orders.CustomerInfoDto;
import lv.wings.model.entity.Customer;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CustomerMapper {
    Customer dtoToEntity(CustomerDataDto dto);

    CustomerInfoDto toDto(Customer customer);

    CustomerFullAdminDto toFullAdminDto(Customer customer);
}
