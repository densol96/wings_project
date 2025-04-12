package lv.wings.mapper;

import org.mapstruct.Mapper;
import lv.wings.dto.response.terminal.TerminalDto;
import lv.wings.model.entity.Terminal;

@Mapper(componentModel = "spring")
public interface TerminalMapper {
    TerminalDto toCheckoutDto(Terminal terminal);
}
