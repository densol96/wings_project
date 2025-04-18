package lv.wings.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/healthcheck")
public class HealthCheckController {

    @GetMapping
    public String healthCheck() {
        return "The server is up";
    }
}
