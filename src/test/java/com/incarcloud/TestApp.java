package com.incarcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl("jdbc:mysql://47.98.211.203:3306/test?characterEncoding=utf8&useSSL=false");
        dataSource.setPassword("maptracking");
        dataSource.setUsername("root");
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        return dataSource;
    }
}
