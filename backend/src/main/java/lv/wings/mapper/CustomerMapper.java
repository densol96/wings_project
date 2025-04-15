package lv.wings.mapper;

import org.mapstruct.Mapper;
import lv.wings.dto.request.payment.CustomerDataDto;
import lv.wings.model.entity.Customer;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CustomerMapper {
    Customer dtoToEntity(CustomerDataDto dto);
}
