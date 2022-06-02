package org.patikadev.finalprojectbatuhanunverdi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@Configuration
public class Configurations {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
