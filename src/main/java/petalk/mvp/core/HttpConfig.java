package petalk.mvp.core;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfig {

    /**
     * RestTemplate 을 Bean 으로 등록합니다.
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Gson 을 Bean 으로 등록합니다.
     * @return Gson
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }
}
