package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.adapter.SocialProfile;
import petalk.mvp.http.auth.adapter.SocialProfileReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.net.URI;
import java.util.Optional;

/**
 * 구글 프로필 http 요청자 인터페이스입니다.
 */
@Component
public class GoogleProfileRequester implements SocialProfileReader {

    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final String PROFILE_URL;
    private final String AUTHORIZATION_KEY = "access_token";
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(GoogleProfileRequester.class);

    public GoogleProfileRequester(RestTemplate restTemplate, Gson gson, @Value("${value.social.google.url.profile}") String profileUrl) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.PROFILE_URL = profileUrl;
    }

    @Override
    public Optional<SocialProfile> getProfile(SocialTokenResponse tokenResponse) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        logger.debug("naver profile request: {}", httpHeaders);

        URI url = UriComponentsBuilder.fromUriString(PROFILE_URL)
                .queryParam(AUTHORIZATION_KEY, tokenResponse.generateKey())
                .encode()
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        logger.debug("google profile response: {}", responseEntity);

        GoogleProfileResponse response = GoogleProfileResponse.from(responseEntity, gson);

        return response.mapProfile();
    }

    @Override
    public boolean isCorrectType(SocialType type) {
        return SocialType.GOOGLE.equals(type);
    }
}
