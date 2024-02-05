package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * 구글 프로필 http 요청자 인터페이스입니다.
 */
@Component
public class GetGoogleProfileRequester implements GetSocialProfileRequester {

    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final String PROFILE_URL;
    private final String AUTHORIZATION_KEY = "access_token";
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(GetGoogleProfileRequester.class);

    public GetGoogleProfileRequester(RestTemplate restTemplate, Gson gson, @Value("${value.social.google.url.profile}") String profileUrl) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.PROFILE_URL = profileUrl;
    }

    @Override
    public Optional<SocialProfile> getProfile(AccessToken accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        logger.debug("naver profile request: {}", httpHeaders);

        URI url = UriComponentsBuilder.fromUriString(PROFILE_URL)
                .queryParam(AUTHORIZATION_KEY, accessToken.generateAuthenticationCode())
                .encode()
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        logger.debug("google profile response: {}", responseEntity);

        GoogleProfileResponse response = GoogleProfileResponse.from(responseEntity, gson);

        return response.mapProfile();
    }
}