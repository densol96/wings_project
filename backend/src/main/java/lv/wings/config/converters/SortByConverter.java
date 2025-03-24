package lv.wings.config.converters;

import org.springframework.stereotype.Component;

import lv.wings.enums.SortBy;

import org.springframework.core.convert.converter.Converter;

@Component
public class SortByConverter implements Converter<String, SortBy> {
    @Override
    public SortBy convert(String source) {
        return SortBy.fromString(source);
    }
}
