package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import petalk.mvp.domain.auth.command.Authenticator;

import java.util.Optional;

/**
 * 구글 액세스 토큰 http 요청자 인터페이스입니다.
 */
@Component
public class GetGoogleTokenRequester implements GetSocialTokenRequester {

    private final GoogleTokenCommandBuilder tokenCommandBuilder;

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;
    private final String TOKEN_URL;
    private final Logger logger = LoggerFactory.getLogger(GetGoogleTokenRequester.class);

    public GetGoogleTokenRequester(
            GoogleTokenCommandBuilder tokenCommandBuilder, RestTemplate restTemplate, Gson gson,
            @Value("${value.social.google.url.token}") String tokenUrl) {
        this.tokenCommandBuilder = tokenCommandBuilder;
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.TOKEN_URL = tokenUrl;
    }

    @Override
    public Optional<AccessToken>getAccessToken(Authenticator authenticator) {
        GetGoogleTokenCommand command = tokenCommandBuilder.generateCommand(authenticator);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);

        logger.debug("naver token request: {}", gson.toJson(command));

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(TOKEN_URL, command, String.class);

        logger.debug("google token response: {}", responseEntity.getBody());

        GoogleTokenResponse response = GoogleTokenResponse.from(responseEntity, gson);

        return response.mapToken();
    }
}
