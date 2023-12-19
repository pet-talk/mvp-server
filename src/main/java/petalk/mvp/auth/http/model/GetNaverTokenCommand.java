package petalk.mvp.auth.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * 네이버 액세스 토큰 요청 모델입니다.
 */
@Builder
public class GetNaverTokenCommand {

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

    public GetNaverTokenCommand(String grantType, String clientId, String clientSecret, String redirectUri, String code, String state) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.code = code;
        this.state = state;
    }
}
