package com.lpc.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 自定义的Date转换类，负责接收前台传回来的时间数据并转为Java的LocalDateTime类
 * 只能改变@RequestParam里的日期。@PathVariable应该也可以
 * @RequestBody里的就不行了
 */
@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        source = source.trim();
        if ("".equals(source)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDateTime(source, "yyyy-MM");
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDateTime(source, "yyyy-MM-dd");
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDateTime(source, "yyyy-MM-dd HH:mm");
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDateTime(source, "yyyy-MM-dd HH:mm:ss");
        } else {
            throw new IllegalArgumentException("Invalid datetime value '" + source + "'");
        }
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return LocalDateTime 日期
     */
    private LocalDateTime parseDateTime(String dateStr, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateStr, dateTimeFormatter);
    }
}
