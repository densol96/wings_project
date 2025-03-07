package lv.wings.controller;

import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.wings.repo.IEventRepo;
import lv.wings.repo.ITestRepo;

@Getter
class TestClass {
    private String PRIVATE_FIELD = "PRIVATE_FIELD";
    public String PUBLIC_FIELD = "PUBLIC_FIELD";
    String FIELD = "FIELD";
}

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ITestRepo testRepo;
    private final IEventRepo eventRepo;

    @GetMapping("/test")
    public String test(Locale locale) {
        System.out.println(locale);
        return "IN TEST";
    }
}
