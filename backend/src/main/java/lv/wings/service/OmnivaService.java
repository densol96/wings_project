package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.terminal.TerminalDto;
import lv.wings.enums.Country;

public interface OmnivaService {

    void syncDbDataWithApi();

    List<TerminalDto> getAllTerminals();

    List<TerminalDto> getAllTerminalsPerCountry(Country country);

}
