package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * 네이버 액세스 토큰 요청 모델입니다.
 */
@Builder
public class NaverTokenCommand {

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("redirect_uri")
    private String redirectUri;

    @JsonProperty("code")
    private String code;

    @JsonProperty("state")
    private String state;

    public NaverTokenCommand(String grantType, String clientId, String clientSecret, String redirectUri, String code, String state) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.code = code;
        this.state = state;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
