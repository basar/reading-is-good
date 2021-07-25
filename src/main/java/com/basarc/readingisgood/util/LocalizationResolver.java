package com.basarc.readingisgood.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class LocalizationResolver {

    private final ApplicationContext applicationContext;

    @Autowired
    public LocalizationResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    public String resolve(final String key) {

        if (!StringUtils.hasText(key)) {
            return "";
        }
        String value;
        try {
            value = applicationContext.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            log.warn("Message not found for the key: {}", key);
            value = key;
        }
        return value;
    }

}

