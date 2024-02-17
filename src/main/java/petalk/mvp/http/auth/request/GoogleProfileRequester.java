package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import petalk.mvp.domain.auth.AccessToken;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.adapter.SocialProfile;
import petalk.mvp.http.auth.adapter.SocialProfileReader;

import java.net.URI;
import java.util.Optional;

/**
 * 구글 프로필 http 요청자 인터페이스입니다.
 */
@Component
@Slf4j
public class GoogleProfileRequester implements SocialProfileReader {

    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;
    private final String PROFILE_URL;
    private final String AUTHORIZATION_KEY = "access_token";

    public GoogleProfileRequester(ObjectMapper objectMapper, RestTemplate restTemplate, @Value("${value.social.google.url.profile}") String profileUrl) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.PROFILE_URL = profileUrl;
    }

    @Override
    public Optional<SocialProfile> getProfile(AccessToken token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(CONTENT_TYPE);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        log.debug("naver profile request: {}", httpHeaders);

        URI url = UriComponentsBuilder.fromUriString(PROFILE_URL)
                .queryParam(AUTHORIZATION_KEY, token.getValue())
                .encode()
                .build()
                .toUri();

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

            log.debug("google profile response: {}", responseEntity);

            GoogleProfile response = objectMapper.readValue(responseEntity.getBody(), GoogleProfile.class);


            if (response != null) {
                return Optional.of(response);
            }

        } catch (HttpClientErrorException e) {
            log.error("구글 프로필 요청 중 클라이언트에 에러가 발생했습니다.", e);
        } catch (RestClientException e) {
            log.error("구글 프로필 요청 중 서버에 에러가 발생했습니다.", e);
        } catch (JsonMappingException e) {
            log.error("구글 프로필 요청 중 응답을 매핑하는 중 에러가 발생했습니다.", e);
        } catch (JsonProcessingException e) {
            log.error("구글 프로필 요청 중 응답을 파싱하는 중 에러가 발생했습니다.", e);
        }catch (Exception e) {
            log.error("구글 프로필 요청 중 알 수 없는 에러가 발생했습니다.", e);
        }
        log.error("구글 프로필 요청 중 응답이 없습니다.");

        return Optional.empty();
    }

    @Override
    public boolean isCorrectType(SocialType type) {
        return SocialType.GOOGLE.equals(type);
    }
}
