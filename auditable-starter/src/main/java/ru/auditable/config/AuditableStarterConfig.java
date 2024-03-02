package ru.auditable.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.auditable.dao.AuditDAO;
import ru.auditable.data.UserData;
import ru.auditable.data.UserInfo;
import ru.auditable.properties.DbProperties;
import ru.auditable.service.AuditService;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ru.auditable")
@EnableConfigurationProperties(DbProperties.class)
public class AuditableStarterConfig {

    @Bean
    @ConditionalOnProperty(name = {"username", "url", "password"})
    public DbProperties properties(){
        return new DbProperties();
    }

    @Bean
    @ConditionalOnBean(name = "properties")
    public DataSource dataSource(DbProperties properties){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setUrl(properties.getUrl());
        return dataSource;
    }

    @Bean
    @ConditionalOnBean(name = "dataSource")
    public JdbcTemplate jdbc(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnBean(name = "jdbc")
    public AuditDAO auditDAO(JdbcTemplate jdbcTemplate){
        return new AuditDAO(jdbcTemplate);
    }

    @Bean
    @ConditionalOnBean(name = "auditDAO")
    public AuditService auditService(AuditDAO auditDAO){
        return new AuditService(auditDAO);
    }

    @Bean
    public UserData userData(){
        return new UserData();
    }

    @Bean
    @ConditionalOnBean(name = "userData")
    public UserInfo userInfo(){
        return new UserInfo();
    }
}
