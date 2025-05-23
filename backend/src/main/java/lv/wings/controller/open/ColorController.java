package lv.wings.controller.open;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.dto.response.color.ColorDto;
import lv.wings.service.ColorService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<ColorDto>> getProductById() {
        log.info("Received GET request on /api/v1/colors.");
        return ResponseEntity.ok().body(colorService.getAllColors());
    }
}
