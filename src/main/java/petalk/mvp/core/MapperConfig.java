package petalk.mvp.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    /**
     * Gson 을 Bean 으로 등록합니다.
     * @return Gson
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }


    /**
     * ObjectMapper 을 Bean 으로 등록합니다.
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Base64 base64() {
        return new Base64();
    }
}
