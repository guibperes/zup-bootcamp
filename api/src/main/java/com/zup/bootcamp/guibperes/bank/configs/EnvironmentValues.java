package com.zup.bootcamp.guibperes.bank.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "com.zup.bootcamp.guibperes.bank.configs.environment")
public class EnvironmentValues {

  private String imagesFolder;
}
