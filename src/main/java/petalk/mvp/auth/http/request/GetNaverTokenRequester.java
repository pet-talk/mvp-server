package petalk.mvp.auth.http.request;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.http.model.AccessToken;
import petalk.mvp.auth.http.model.GetNaverTokenCommand;
import petalk.mvp.auth.http.model.NaverTokenCommandBuilder;
import petalk.mvp.auth.http.model.NaverTokenResponse;

import java.util.Optional;

/**
 * 네이버 액세스 토큰 http 요청자 인터페이스입니다.
 */
@Component
public class GetNaverTokenRequester implements GetSocialTokenRequester {

    private final NaverTokenCommandBuilder naverTokenCommandBuilder;

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_FORM_URLENCODED;
    private final String TOKEN_URL;

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
        HttpEntity<GetNaverTokenCommand> request = new HttpEntity<>(command, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(TOKEN_URL, request, String.class);
        NaverTokenResponse response = NaverTokenResponse.from(responseEntity);

        return response.mapToken(gson);
    }
}
