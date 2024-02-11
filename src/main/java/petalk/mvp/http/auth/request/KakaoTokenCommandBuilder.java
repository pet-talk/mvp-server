package petalk.mvp.http.auth.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

//auth domain import
import petalk.mvp.domain.auth.AuthorizationCode;

/**
 * 카카오 토큰 http 요청 커맨드 빌더입니다.
 */
@Component
public class KakaoTokenCommandBuilder {

    private final String clientId;
    private final String redirectId;
    private final String clientSecret;
    private final String grantType;
    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_FORM_URLENCODED;


    public KakaoTokenCommandBuilder(
            @Value("${value.social.kakao.client_id}") String clientId,
            @Value("${value.social.kakao.redirect}") String redirectId,
            @Value("${value.social.kakao.client_secret}") String clientSecret,
            @Value("${value.social.kakao.grant_type}") String grantType) {
        this.clientId = clientId;
        this.redirectId = redirectId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
    }

    public HttpEntity<MultiValueMap<String, String>> generateCommand(AuthorizationCode code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(CONTENT_TYPE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectId);
        params.add("code", code.getValue());

        return new HttpEntity<>(params, headers);

    }
}
