package petalk.mvp.http.auth.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.adapter.SocialProfileKeyReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.net.URI;
import java.util.Optional;

/**
 * 네이버 액세스 토큰 http 요청자 인터페이스입니다.
 */
@Component
@Slf4j
public class NaverTokenRequester implements SocialProfileKeyReader {

    private final NaverTokenCommandBuilder naverTokenCommandBuilder;
    private final RestTemplate restTemplate;

    public NaverTokenRequester(
            NaverTokenCommandBuilder naverTokenCommandBuilder, RestTemplate restTemplate) {
        this.naverTokenCommandBuilder = naverTokenCommandBuilder;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<SocialTokenResponse> getKey(AuthorizationCode code) {
        URI url = naverTokenCommandBuilder.generateCommand(code);

        try {
            ResponseEntity<NaverTokenResponse> responseEntity = restTemplate.getForEntity(url, NaverTokenResponse.class);

            NaverTokenResponse response = responseEntity.getBody();

            log.debug("naver token response: {}", response);

            if (responseEntity.getStatusCode().is2xxSuccessful() && response != null) {
                return Optional.of(response);
            }
        } catch (Exception e) {
            log.error("naver token request error");
        }
        return Optional.empty();
    }

    @Override
    public boolean isCorrectType(SocialType type) {
        return SocialType.NAVER.equals(type);
    }
}
