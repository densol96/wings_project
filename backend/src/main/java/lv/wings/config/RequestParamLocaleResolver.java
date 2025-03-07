package lv.wings.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;

public class RequestParamLocaleResolver implements LocaleResolver {
    private Locale defaultLocale = Locale.forLanguageTag("lv");
    private String paramName = "lang";
    private List<String> allowedLocales = new ArrayList<>(List.of("lv", "en"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getParameter(paramName);
        if (lang != null && allowedLocales.contains(lang)) {
            try {
                return Locale.forLanguageTag(lang);
            } catch (Exception e) {
                return defaultLocale;
            }
        }
        return defaultLocale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }
}