package com.incarcloud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;


/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/13 11:06 AM
 * @Version 1.0
 */
@SpringBootApplication
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class);
    }


    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(newDataSource());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource newDataSource() {
        return DataSourceBuilder.create().build();
    }

}
