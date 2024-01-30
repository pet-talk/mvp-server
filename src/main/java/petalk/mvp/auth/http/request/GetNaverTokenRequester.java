package petalk.mvp.auth.http.request;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.http.model.AccessToken;
import petalk.mvp.auth.http.model.GetNaverTokenCommand;
import petalk.mvp.auth.http.model.NaverTokenCommandBuilder;
import petalk.mvp.auth.http.model.NaverTokenResponse;

import java.net.URI;
import java.util.Optional;

/**
 * 네이버 액세스 토큰 http 요청자 인터페이스입니다.
 */
@Component
public class GetNaverTokenRequester implements GetSocialTokenRequester {

    private final NaverTokenCommandBuilder naverTokenCommandBuilder;

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;
    private final String TOKEN_URL;
    private final Logger logger = LoggerFactory.getLogger(GetNaverTokenRequester.class);

    public GetNaverTokenRequester(
            NaverTokenCommandBuilder naverTokenCommandBuilder, RestTemplate restTemplate, Gson gson,
            @Value("${value.social.naver.url.token}") String tokenUrl) {
        this.naverTokenCommandBuilder = naverTokenCommandBuilder;
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.TOKEN_URL = tokenUrl;
    }

    @Override
    public Optional<AccessToken>getAccessToken(Authenticator authenticator) {
        GetNaverTokenCommand command = naverTokenCommandBuilder.getCommand(authenticator);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);

        logger.debug("naver token request: {}", gson.toJson(command));

        URI url = UriComponentsBuilder.fromUriString(TOKEN_URL)
                .queryParam("client_id", command.getClientId())
                .queryParam("client_secret", command.getClientSecret())
                .queryParam("grant_type", command.getGrantType())
                .queryParam("redirect_uri", command.getRedirectUri())
                .queryParam("state", command.getState())
                .queryParam("code", command.getCode())
                .encode()
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        logger.debug("naver token response: {}", responseEntity.getBody());

        NaverTokenResponse response = NaverTokenResponse.from(responseEntity, gson);

        return response.mapToken();
    }
}
