package com.jack.openapiclientsdk;

import com.jack.openapiclientsdk.client.JackClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("openapi.client")
@Data
@ComponentScan
public class OpenApiClientConifg {

    private String accessKey;

    private String secretKey;

    @Bean
    public JackClient jackClient(){
        return new JackClient(accessKey,secretKey);

    }
}
