package petalk.mvp.auth.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * 구글 액세스 토큰 요청 모델입니다.
 */
@Builder
public class GetGoogleTokenCommand {

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

    public GetGoogleTokenCommand(String grantType, String clientId, String clientSecret, String redirectUri, String code) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.code = code;
    }
}
