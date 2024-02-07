package petalk.mvp.http.auth.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import petalk.mvp.domain.auth.AuthorizationCode;

/**
 * 구글 토큰 http 요청 커맨드 빌더입니다.
 */
@Component
public class GoogleTokenCommandBuilder {

    private final String CLIENT_ID;

    private final String REDIRECT_ID;

    private final String CLIENT_SECRET;

    private final String GRANT_TYPE;

    public GoogleTokenCommandBuilder(
            @Value("${value.social.google.client_id}") String clientId,
            @Value("${value.social.google.redirect}") String redirectId,
            @Value("${value.social.google.client_secret}") String clientSecret,
            @Value("${value.social.google.grant_type}") String grantType) {
        this.CLIENT_ID = clientId;
        this.REDIRECT_ID = redirectId;
        this.CLIENT_SECRET = clientSecret;
        this.GRANT_TYPE = grantType;
    }

    public GoogleTokenCommand generateCommand(AuthorizationCode code) {

        return GoogleTokenCommand.builder()
                .clientSecret(CLIENT_SECRET)
                .clientId(CLIENT_ID)
                .grantType(GRANT_TYPE)
                .redirectUri(REDIRECT_ID)
                .code(code.getValue())
                .build();

    }
}
