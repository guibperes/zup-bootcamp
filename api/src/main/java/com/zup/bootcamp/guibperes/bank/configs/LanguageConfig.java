package com.zup.bootcamp.guibperes.bank.configs;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LanguageConfig {

  @Bean
  public LocaleResolver localeResolver() {
    var sessionLocaleResolver = new SessionLocaleResolver();

    sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);

    return sessionLocaleResolver;
  }
}
