package petalk.mvp.http.auth.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import petalk.mvp.domain.auth.AuthorizationCode;

@Component
public class NaverTokenCommandBuilder {

    private final String CLIENT_ID;

    private final String REDIRECT_ID;

    private final String CLIENT_SECRET;

    private final String GRANT_TYPE;

    private final String STATE;

    public NaverTokenCommandBuilder(
            @Value("${value.social.naver.client_id}") String clientId,
            @Value("${value.social.naver.redirect}") String redirectId,
            @Value("${value.social.naver.client_secret}") String clientSecret,
            @Value("${value.social.naver.grant_type}") String grantType,
            @Value("${value.social.naver.state}") String state) {
        this.CLIENT_ID = clientId;
        this.REDIRECT_ID = redirectId;
        this.CLIENT_SECRET = clientSecret;
        this.GRANT_TYPE = grantType;
        this.STATE = state;
    }

    public NaverTokenCommand generateCommand(AuthorizationCode code) {

        return NaverTokenCommand.builder()
                .clientSecret(CLIENT_SECRET)
                .clientId(CLIENT_ID)
                .grantType(GRANT_TYPE)
                .redirectUri(REDIRECT_ID)
                .state(STATE)
                .code(code.getValue())
                .build();

    }
}
