package petalk.mvp.auth.http.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.domain.AuthorizationCode;

@Component
public class NaverTokenCommandBuilder {

    private final String CLIENT_ID;

    private final String REDIRECT_ID;

    private final String CLIENT_SECRET;

    private final String GRANT_TYPE;

    private final String STATE;

    public NaverTokenCommandBuilder(
            @Value("${spring.social.naver.client_id}") String clientId,
            @Value("${spring.social.naver.redirect}") String redirectId,
            @Value("${spring.social.naver.client_secret}") String clientSecret,
            @Value("${spring.social.naver.grant_type}") String grantType,
            @Value("${spring.social.naver.state}") String state) {
        this.CLIENT_ID = clientId;
        this.REDIRECT_ID = redirectId;
        this.CLIENT_SECRET = clientSecret;
        this.GRANT_TYPE = grantType;
        this.STATE = state;
    }

    public GetNaverTokenCommand getCommand(Authenticator authenticator) {
        AuthorizationCode authorizationCode = authenticator.getAuthorizationCode();

        return GetNaverTokenCommand.builder()
                .clientSecret(CLIENT_SECRET)
                .clientId(CLIENT_ID)
                .grantType(GRANT_TYPE)
                .redirectUri(REDIRECT_ID)
                .state(STATE)
                .code(authorizationCode.getValue())
                .build();

    }
}
