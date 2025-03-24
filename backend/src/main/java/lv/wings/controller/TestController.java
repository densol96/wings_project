package lv.wings.controller;

import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lv.wings.repo.TestRepository;

@Getter
class TestClass {
    private String PRIVATE_FIELD = "PRIVATE_FIELD";
    public String PUBLIC_FIELD = "PUBLIC_FIELD";
    String FIELD = "FIELD";
}


@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepo;

    @GetMapping("/test/{id}")
    public String test(@PathVariable Integer id) {
        System.out.println("IN CONTROLLER => " + id);
        return "IN TEST";
    }
}
