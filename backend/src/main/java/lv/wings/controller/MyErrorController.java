package lv.wings.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;


  
@Controller
@RequestMapping("/error")
public class MyErrorController implements ErrorController{

     
    @GetMapping("path")
    public ModelAndView redirectAllErrorsToReact() {
        return new ModelAndView("redi:../");
    }

    
    
}
    


