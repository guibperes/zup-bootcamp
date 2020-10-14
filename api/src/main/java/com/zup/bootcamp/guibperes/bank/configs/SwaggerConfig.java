package com.zup.bootcamp.guibperes.bank.configs;

import com.zup.bootcamp.guibperes.bank.BankApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("", "/swagger-ui/index.html");
    registry.addRedirectViewController("/", "/swagger-ui/index.html");
    registry.addRedirectViewController("/swagger", "/swagger-ui/index.html");
  }

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage(BankApplication.class.getPackageName()))
      .paths(PathSelectors.any())
      .build()
      .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder()
      .title("Zup Bootcamp Bank API")
      .description("Zup Bootcamp Project")
      .version("v1.0.0")
      .license("MIT")
      .build();
  }
}
