package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * 구글 액세스 토큰 요청 모델입니다.
 */
@Builder
public class GoogleTokenCommand {

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

    public GoogleTokenCommand(String grantType, String clientId, String clientSecret, String redirectUri, String code) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.code = code;
    }
}
