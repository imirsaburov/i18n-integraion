package com.imirsaburov.i18nintegration.messagesourse;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class CustomMessageSource extends AbstractMessageSource {

    private final BiFunction<String, String, String> function;

    @Override
    protected MessageFormat resolveCode(String key, Locale locale) {

        String content = function.apply(key, locale.toString());

        return new MessageFormat(content, locale);
    }
}
