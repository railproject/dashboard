package org.springframework.samples.portfolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    private @Value("${db.show_sql}") String hibernateShowSql;
    private @Value("${db.dialect}") String hibernateDialect;
    private @Value("${db.hbm2ddl_auto}") String hibernateHbm2ddlAuto;

    @Bean
    public DataSource getDataSource() {
        System.out.println("url====================================================" + url);
        System.out.println("driver====================================================" + driver);
        System.out.println("username====================================================" + username);
        System.out.println("password====================================================" + password);
        return null;
    }
}
