package lv.wings.config.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // page, resultsPerPage, id ==== BASIC FORMAT CONTROLL
        registry.addInterceptor(new ParameterValidationInterceptor()).addPathPatterns("/**");
    }
}
