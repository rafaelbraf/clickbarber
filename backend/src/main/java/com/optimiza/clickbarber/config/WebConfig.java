package com.optimiza.clickbarber.config;

import com.optimiza.clickbarber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtRequestFilter jwtRequestFilter;
    private final PropertiesConfig propertiesConfig;

    @Autowired
    public WebConfig(JwtRequestFilter jwtRequestFilter, PropertiesConfig propertiesConfig) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        propertiesConfig.FRONTEND_ADMIN_URL,
                        propertiesConfig.FRONTEND_CLIENTE_URL)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtFilter() {
        var registrationBean = new FilterRegistrationBean<JwtRequestFilter>();
        registrationBean.setFilter(jwtRequestFilter);

        var listUrlPatterns = List.of(
                Constants.UrlPattern.BARBEARIAS_URL_PATTERN,
                Constants.UrlPattern.SERVICOS_URL_PATTERN,
                Constants.UrlPattern.BARBEIROS_URL_PATTERN,
                Constants.UrlPattern.AGENDAMENTOS_URL_PATTERN,
                Constants.UrlPattern.FORMAS_PAGAMENTO_URL_PATTERN);

        var collectionUrlPatterns = new ArrayList<>(listUrlPatterns);
        registrationBean.setUrlPatterns(collectionUrlPatterns);

        return registrationBean;
    }

}
