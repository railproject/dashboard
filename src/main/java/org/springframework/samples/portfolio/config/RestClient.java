package org.springframework.samples.portfolio.config;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by star on 4/24/14.
 */
@Configuration
public class RestClient {

    private static Log logger = LogFactory.getLog(RestClient.class);

    @Value("${kanban.rest.url}")
    private String rest_url;

    @Bean
    public WebResource getWebResource() {
        Client client = Client.create();
        logger.info(this.rest_url);
        return client.resource(this.rest_url);
    }
}
