package com.lpc.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 从com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer类复制过来进行修改
 */
public class LocalDateTimeDeserializer extends JSR310DateTimeDeserializerBase<LocalDateTime> {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DEFAULT_FORMATTER;
    public static final LocalDateTimeDeserializer INSTANCE;

    static {
        DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        INSTANCE = new LocalDateTimeDeserializer();
    }

    private LocalDateTimeDeserializer() {
        this(DEFAULT_FORMATTER);
    }

    public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
        super(LocalDateTime.class, formatter);
    }

    protected LocalDateTimeDeserializer(LocalDateTimeDeserializer base, Boolean leniency) {
        super(base, leniency);
    }

    @Override
    protected LocalDateTimeDeserializer withDateFormat(DateTimeFormatter formatter) {
        return new LocalDateTimeDeserializer(formatter);
    }

    @Override
    protected LocalDateTimeDeserializer withLeniency(Boolean leniency) {
        return new LocalDateTimeDeserializer(this, leniency);
    }

    /**
     * 关键方法
     */
    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.hasTokenId(6)) {
            // 修改了这个分支里面的代码
            String string = parser.getText().trim();
            if (string.length() == 0) {
                return !this.isLenient() ? (LocalDateTime) this._failForNotLenient(parser, context, JsonToken.VALUE_STRING) : null;
            } else {
                return convert(string);
            }
        } else {
            if (parser.isExpectedStartArrayToken()) {
                JsonToken t = parser.nextToken();
                if (t == JsonToken.END_ARRAY) {
                    return null;
                }

                LocalDateTime result;
                if ((t == JsonToken.VALUE_STRING || t == JsonToken.VALUE_EMBEDDED_OBJECT) && context.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                    result = this.deserialize(parser, context);
                    if (parser.nextToken() != JsonToken.END_ARRAY) {
                        this.handleMissingEndArrayForSingle(parser, context);
                    }

                    return result;
                }

                if (t == JsonToken.VALUE_NUMBER_INT) {
                    int year = parser.getIntValue();
                    int month = parser.nextIntValue(-1);
                    int day = parser.nextIntValue(-1);
                    int hour = parser.nextIntValue(-1);
                    int minute = parser.nextIntValue(-1);
                    t = parser.nextToken();
                    if (t == JsonToken.END_ARRAY) {
                        result = LocalDateTime.of(year, month, day, hour, minute);
                    } else {
                        int second = parser.getIntValue();
                        t = parser.nextToken();
                        if (t == JsonToken.END_ARRAY) {
                            result = LocalDateTime.of(year, month, day, hour, minute, second);
                        } else {
                            int partialSecond = parser.getIntValue();
                            if (partialSecond < 1000 && !context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
                                partialSecond *= 1000000;
                            }

                            if (parser.nextToken() != JsonToken.END_ARRAY) {
                                throw context.wrongTokenException(parser, this.handledType(), JsonToken.END_ARRAY, "Expected array to end");
                            }

                            result = LocalDateTime.of(year, month, day, hour, minute, second, partialSecond);
                        }
                    }

                    return result;
                }

                context.reportInputMismatch(this.handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", new Object[]{t});
            }

            if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
                return (LocalDateTime) parser.getEmbeddedObject();
            } else {
                if (parser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                    this._throwNoNumericTimestampNeedTimeZone(parser, context);
                }

                return (LocalDateTime) this._handleUnexpectedToken(context, parser, "Expected array or string.", new Object[0]);
            }
        }
    }

    private LocalDateTime convert(String source) {
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

