package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.adapter.SocialProfileKeyReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.util.Optional;

/**
 * 구글 액세스 토큰 http 요청자 인터페이스입니다.
 */
@Component
@Slf4j
public class GoogleTokenRequester implements SocialProfileKeyReader {

    private final GoogleTokenCommandBuilder tokenCommandBuilder;

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;
    private final String TOKEN_URL;

    public GoogleTokenRequester(
            GoogleTokenCommandBuilder tokenCommandBuilder, RestTemplate restTemplate, Gson gson,
            @Value("${value.social.google.url.token}") String tokenUrl) {
        this.tokenCommandBuilder = tokenCommandBuilder;
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.TOKEN_URL = tokenUrl;
    }

    @Override
    public Optional<SocialTokenResponse> getKey(AuthorizationCode code) {
        GoogleTokenCommand command = tokenCommandBuilder.generateCommand(code);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);

        log.debug("naver token request: {}", gson.toJson(command));

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(TOKEN_URL, command, String.class);

        log.debug("google token response: {}", responseEntity.getBody());

        GoogleTokenResponse response = GoogleTokenResponse.from(responseEntity, gson);

        return response.mapToken();
    }

    @Override
    public boolean isCorrectType(SocialType type) {
        return SocialType.GOOGLE.equals(type);
    }
}
