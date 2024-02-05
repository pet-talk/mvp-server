package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * 네이버 프로필 http 요청자 인터페이스입니다.
 */
@Component
public class GetNaverProfileRequester implements GetSocialProfileRequester {

    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final String PROFILE_URL;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(GetNaverProfileRequester.class);

    public GetNaverProfileRequester(RestTemplate restTemplate, Gson gson, @Value("${value.social.naver.url.profile}") String profileUrl) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.PROFILE_URL = profileUrl;
    }

    @Override
    public Optional<SocialProfile> getProfile(AccessToken accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);
        httpHeaders.set(AUTHORIZATION_HEADER, accessToken.generateAuthenticationCode());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        logger.debug("naver profile request: {}", httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(PROFILE_URL, HttpMethod.GET, request, String.class);

        logger.debug("naver profile response: {}", responseEntity);

        NaverProfileResponse response = NaverProfileResponse.from(responseEntity, gson);

        return response.mapProfile();
    }
}