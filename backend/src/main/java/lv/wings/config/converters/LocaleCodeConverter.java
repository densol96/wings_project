package lv.wings.config.converters;

import org.springframework.stereotype.Component;
import lv.wings.enums.LocaleCode;
import org.springframework.core.convert.converter.Converter;

@Component
public class LocaleCodeConverter implements Converter<String, LocaleCode> {

    @Override
    public LocaleCode convert(String source) {
        return LocaleCode.from(source);
    }
}
