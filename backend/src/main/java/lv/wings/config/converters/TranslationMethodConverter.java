package lv.wings.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import lv.wings.enums.TranslationMethod;

@Component
public class TranslationMethodConverter implements Converter<String, TranslationMethod> {
    @Override
    public TranslationMethod convert(String source) {
        try {
            return TranslationMethod.valueOf(source.toUpperCase());
        } catch (Exception e) {
            return TranslationMethod.AUTO;
        }
    }
}

