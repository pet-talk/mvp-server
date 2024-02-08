package petalk.mvp.http.auth.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import petalk.mvp.domain.auth.AuthorizationCode;

import java.net.URI;

@Component
public class NaverTokenCommandBuilder {

    private final String clientId;

    private final String redirectUrl;

    private final String clientSecret;

    private final String grantType;

    private final String state;
    private final String url;

    public NaverTokenCommandBuilder(
            @Value("${value.social.naver.client_id}") String clientId,
            @Value("${value.social.naver.redirect}") String redirectUrl,
            @Value("${value.social.naver.client_secret}") String clientSecret,
            @Value("${value.social.naver.grant_type}") String grantType,
            @Value("${value.social.naver.state}") String state,
            @Value("${value.social.naver.url.token}") String tokenUrl) {
        this.clientId = clientId;
        this.redirectUrl = redirectUrl;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.state = state;
        this.url = tokenUrl;
    }

    public URI generateCommand(AuthorizationCode code) {

        return UriComponentsBuilder.fromUriString(url)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", grantType)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("state", state)
                .queryParam("code", code)
                .encode()
                .build()
                .toUri();

    }
}
