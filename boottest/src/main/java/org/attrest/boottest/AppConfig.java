package org.attrest.boottest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@ComponentScan(basePackages = "org.attrest.core")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AppConfig {
    
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource cSource = new UrlBasedCorsConfigurationSource();
        cSource.registerCorsConfiguration("/**", configuration);
        CorsFilter cf = new CorsFilter(cSource);
        return cf;
    }
}