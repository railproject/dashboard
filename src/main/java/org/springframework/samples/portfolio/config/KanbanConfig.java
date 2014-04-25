package org.springframework.samples.portfolio.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Created by star on 4/24/14.
 */
@Configuration
@Import(RestClient.class)
public class KanbanConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
        PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
        props.setLocations(new Resource[] {new ClassPathResource("kanban.properties"), new ClassPathResource("datasource.properties")});
        return props;
    }

}
