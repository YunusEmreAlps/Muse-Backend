package com.muse.spring.login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Authentication"))
        .components(new Components().addSecuritySchemes("Authentication", createCustomApiKeyScheme()))
        .info(new Info().title("Muse API").version("1.0.0")
            .description("Muse API Services")
            .description("This application is designed to guide users through their financial journey with inspiration, wisdom, and harmony. Our goal is to provide a seamless and insightful banking experience that helps users manage their finances efficiently and effectively.")
            .version("0.0.1")
            .contact(new Contact().name("Yunus Emre Alpu").email("YunusAlpu@icloud.com")));
  }

  private SecurityScheme createCustomApiKeyScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");
  }
}
