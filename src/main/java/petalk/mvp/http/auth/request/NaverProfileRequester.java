package petalk.mvp.http.auth.request;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.adapter.SocialProfile;
import petalk.mvp.http.auth.adapter.SocialProfileReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.util.Optional;

/**
 * 네이버 프로필 http 요청자 인터페이스입니다.
 */
@Component
public class NaverProfileRequester implements SocialProfileReader {

    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    private final RestTemplate restTemplate;
    private final String PROFILE_URL;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(NaverProfileRequester.class);

    public NaverProfileRequester(RestTemplate restTemplate, @Value("${value.social.naver.url.profile}") String profileUrl) {
        this.restTemplate = restTemplate;
        this.PROFILE_URL = profileUrl;
    }

    @Override
    public Optional<SocialProfile> getProfile(SocialTokenResponse tokenResponse) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);
        httpHeaders.set(AUTHORIZATION_HEADER, tokenResponse.generateKey());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        logger.debug("naver profile request: {}", tokenResponse.generateKey());

        try {
            ResponseEntity<NaverProfile> responseEntity = restTemplate.exchange(PROFILE_URL, HttpMethod.GET, request, NaverProfile.class);

            NaverProfile response = responseEntity.getBody();
            logger.debug("naver profile response: {}", response);

            if (responseEntity.getStatusCode().is2xxSuccessful() && response != null) {
                return Optional.of(response);
            }

        } catch (Exception e) {
            logger.error("naver profile request error", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean isCorrectType(SocialType type) {
        return SocialType.NAVER.equals(type);
    }
}
