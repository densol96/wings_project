package lv.wings.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lv.wings.dto.response.terminal.TerminalDto;
import lv.wings.service.OmnivaService;

@RestController
@RequestMapping("/api/v1/terminals")
@RequiredArgsConstructor
public class OmnivaController {

    private final OmnivaService omnivaService;

    @PutMapping("/update")
    public ResponseEntity<String> updateTerminals() {
        omnivaService.syncDbDataWithApi();
        return ResponseEntity.ok().body("DB updated");
    }

    @GetMapping
    public ResponseEntity<List<TerminalDto>> getAllTerminals() {
        return ResponseEntity.ok().body(omnivaService.getAllTerminals());
    }
}
