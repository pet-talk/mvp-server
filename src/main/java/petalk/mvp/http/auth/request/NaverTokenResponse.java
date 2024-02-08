package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

/**
 * 네이버 서비스의 액세스 토큰을 나타냅니다.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class NaverTokenResponse implements SocialTokenResponse {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;

    @JsonCreator
    public NaverTokenResponse(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("expires_in") String expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = (expiresIn != null) ? Integer.parseInt(expiresIn) :  0;

        this.validate();
    }

    public void validate() {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("accessToken is null or empty");
        }
        if (tokenType == null || tokenType.isBlank()) {
            throw new IllegalArgumentException("tokenType is null or empty");
        }
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("refreshToken is null or empty");
        }
        if (expiresIn <= 0) {
            throw new IllegalArgumentException("expiresIn is less than or equal to zero");
        }
    }

    @Override
    public String generateKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(tokenType);
        sb.append(" ");
        sb.append(accessToken);
        return sb.toString();
    }
}