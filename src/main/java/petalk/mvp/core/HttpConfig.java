package petalk.mvp.core;

import com.google.gson.Gson;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class HttpConfig {

    private final Integer CONNECT_TIMEOUT_SECOND = 5;
    private final Integer READ_TIMEOUT_SECOND = 5;

    /**
     * RestTemplate 을 Bean 으로 등록합니다.
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECOND))
                .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SECOND))
                .build();
    }

}
