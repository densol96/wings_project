package lv.wings.mapper;

import org.mapstruct.Mapper;
import lv.wings.dto.request.payment.AddressDto;
import lv.wings.dto.response.admin.orders.AddressAdminDto;
import lv.wings.model.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address dtoToEntity(AddressDto dto);

    AddressAdminDto toAdminDto(Address address);
}
