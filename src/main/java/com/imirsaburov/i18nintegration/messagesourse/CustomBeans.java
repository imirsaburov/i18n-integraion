package com.imirsaburov.i18nintegration.messagesourse;

import com.imirsaburov.i18nintegration.translation.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("MessageSourceCustomBeans")
@RequiredArgsConstructor
public class CustomBeans {

    private final TranslationService translationService;


    @Bean("messageSource")
    public MessageSource messageSource() {
        return new CustomMessageSource(translationService::getContent);
    }

}
