package com.zup.bootcamp.guibperes.bank.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class EnvironmentConfig {

  @Bean
  public EnvironmentVariables environmentVariablesBean() {
    return EnvironmentVariables.getInstance();
  }

  @Getter
  public static class EnvironmentVariables {

    private static final EnvironmentVariables instance = new EnvironmentVariables();

    @Value("${com.zup.bootcamp.guibperes.bank.configs.EnvironmentConfig.application-url}")
    private String applicationUrl;

    @Value("${com.zup.bootcamp.guibperes.bank.configs.EnvironmentConfig.images-folder}")
    private String imagesFolder;

    private EnvironmentVariables() {}

    public static EnvironmentVariables getInstance() {
      return instance;
    }
  }
}
