package com.application.ProgramX.config;

import com.application.ProgramX.service.message.english.EnglishMessage;
import com.application.ProgramX.service.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "datasource")
public class DatasourceConfig {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("datasource.driverClassName")));
        dataSource.setUrl(env.getProperty("datasource.url"));
        dataSource.setUsername(env.getProperty("datasource.user"));
        dataSource.setPassword(env.getProperty("datasource.password"));
        return dataSource;
    }

    @Bean
    public Message message(){
        return new EnglishMessage();
    }

}
