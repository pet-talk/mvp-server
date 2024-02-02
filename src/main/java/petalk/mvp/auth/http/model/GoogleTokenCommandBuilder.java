package petalk.mvp.auth.http.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import petalk.mvp.auth.domain.command.Authenticator;
import petalk.mvp.auth.domain.command.AuthorizationCode;

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

    public GetGoogleTokenCommand generateCommand(Authenticator authenticator) {
        AuthorizationCode authorizationCode = authenticator.getAuthorizationCode();

        return GetGoogleTokenCommand.builder()
                .clientSecret(CLIENT_SECRET)
                .clientId(CLIENT_ID)
                .grantType(GRANT_TYPE)
                .redirectUri(REDIRECT_ID)
                .code(authorizationCode.getValue())
                .build();

    }
}
