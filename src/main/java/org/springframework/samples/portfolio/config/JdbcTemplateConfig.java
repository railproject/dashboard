package org.springframework.samples.portfolio.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.samples.portfolio.dao.JdbcService;
import org.springframework.samples.portfolio.dao.JdbcServiceImpl;

import javax.sql.DataSource;


/**
 * Created by star on 4/22/14.
 */
@Configuration
public class JdbcTemplateConfig {

    private @Value("${db.url}") String url;
    private @Value("${db.username}") String username;
    private @Value("${db.passwd}") String password;
    private @Value("${db.driver}") String driver;


    @Bean(destroyMethod = "close")
    public DataSource getDataSource() {
        System.out.println("url====================================================" + url);
        System.out.println("driver====================================================" + driver);
        System.out.println("username====================================================" + username);
        System.out.println("password====================================================" + password);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.driver);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate;
    }

    @Bean
    public JdbcService getJdbcService() {
        return new JdbcServiceImpl();
    }
/*
    public JdbcService getJdbcService() {
        JdbcServiceImpl jdbcService = new JdbcServiceImpl();
        jdbcService.createJdbcTemplate(getDataSource());
        return jdbcService;
    }*/
}
