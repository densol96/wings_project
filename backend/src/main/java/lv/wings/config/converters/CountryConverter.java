package lv.wings.config.converters;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import lv.wings.enums.Country;
import org.springframework.core.convert.converter.Converter;

@Component
@Slf4j
public class CountryConverter implements Converter<String, Country> {

    @Override
    public Country convert(String source) {
        try {
            return Country.valueOf(source.toUpperCase());
        } catch (Exception e) {
            log.error("Unable to convert String {} to enum type Country. Fallback to default LV");
            return Country.LV;
        }

    }

}
