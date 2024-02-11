package petalk.mvp.http.auth.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

// import auth domain
import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialType;

// import auth adapter
import petalk.mvp.http.auth.adapter.SocialProfileKeyReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.util.Optional;

/**
 * 카카오 id 토큰 http 요청 구현체입니다.
 */
@Component
@Slf4j
public class KakaoIdTokenRequester implements SocialProfileKeyReader {

    private final KakaoTokenCommandBuilder tokenCommandBuilder;
    private final RestTemplate restTemplate;
    private final String TOKEN_URL;

    public KakaoIdTokenRequester(
            KakaoTokenCommandBuilder tokenCommandBuilder, RestTemplate restTemplate,
            @Value("${value.social.kakao.url.token}") String tokenUrl) {
        this.tokenCommandBuilder = tokenCommandBuilder;
        this.restTemplate = restTemplate;
        this.TOKEN_URL = tokenUrl;
    }

    @Override
    public Optional<SocialTokenResponse> getKey(AuthorizationCode code) {
        HttpEntity<MultiValueMap<String, String>> command = tokenCommandBuilder.generateCommand(code);

        ResponseEntity<KakaoTokenResponse> responseEntity = restTemplate.postForEntity(TOKEN_URL, command, KakaoTokenResponse.class);

        KakaoTokenResponse response = responseEntity.getBody();
        log.debug("kakao token response: {}", response);

        if (responseEntity.getStatusCode().is2xxSuccessful() && response != null) {
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public boolean isCorrectType(SocialType type) {
        return SocialType.KAKAO.equals(type);
    }
}
