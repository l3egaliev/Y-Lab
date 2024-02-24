package ru.loggable.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.loggable.aspects.LoggableAspect;
import ru.loggable.property.AppProperties;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class LoggableStarterConfig {

    @Bean
    @ConditionalOnProperty(name = "enabled")
    public AppProperties appProperties(){
        return new AppProperties();
    }

    @Bean
    public LoggableAspect loggableAspect(AppProperties properties){
        if (properties.getEnabled()) {
            return new LoggableAspect();
        }
        return null;
    }
}
