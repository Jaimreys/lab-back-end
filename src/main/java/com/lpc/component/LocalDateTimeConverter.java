package com.lpc.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义的Date转换类，负责接收前台传回来的时间数据并转为Java的LocalDateTime类
 * 只能改变@RequestParam里的日期。@PathVariable应该也可以
 * 在@RequestBody里的就不行了
 */
@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    private static final DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime convert(String source) {
        source = source.trim();
        if ("".equals(source)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            // yyyy-MM
            return LocalDateTime.parse(source + "-01 00:00:00", dateTimeFormatter);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            // yyyy-MM-dd
            return LocalDateTime.parse(source + " 00:00:00", dateTimeFormatter);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            // yyyy-MM-dd HH:mm
            return LocalDateTime.parse(source + ":00", dateTimeFormatter);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            // yyyy-MM-dd HH:mm:ss
            return LocalDateTime.parse(source, dateTimeFormatter);
        } else {
            throw new IllegalArgumentException("Invalid datetime value '" + source + "'");
        }
    }

}
