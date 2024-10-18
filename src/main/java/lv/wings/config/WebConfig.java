package lv.wings.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



// Atļauja iegūt datus no api
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") 
                .allowedMethods("*") 
                .allowCredentials(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{spring:(?!static|api)[^\\.]*}")
                .setViewName("forward:/index.html");
        registry.addViewController("/**/{spring:(?!static|api)[^\\.]*}")
                .setViewName("forward:/index.html");
    }
}
