package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.terminal.TerminalDto;

public interface OmnivaService {

    void syncDbDataWithApi();

    List<TerminalDto> getAllTerminals();

}
