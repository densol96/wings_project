package lv.wings.controller;

import org.springframework.stereotype.Controller;

@Controller
public class ReactAppController {
    //@GetMapping(value = {"/{path:[^\\.]*}"})
    public String redirect() {
        System.out.println("try redirect");
        return "forward:/index.html";
    }
}
