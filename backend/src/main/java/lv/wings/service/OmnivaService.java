package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.terminal.TerminalDto;
import lv.wings.enums.Country;
import lv.wings.model.entity.Terminal;

public interface OmnivaService extends CRUDService<Terminal, Integer> {

    void syncDbDataWithApi();

    List<TerminalDto> getAllTerminals();

    List<TerminalDto> getAllTerminalsPerCountry(Country country);

}
