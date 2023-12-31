package petalk.mvp.auth.http.model;

import com.google.gson.annotations.SerializedName;

/**
 * third party 서비스의 액세스 토큰을 나타냅니다.
 */
public class NaverAccessToken implements AccessToken {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("expires_in")
    private long expiresIn;

    public NaverAccessToken(String accessToken, String tokenType, String refreshToken, long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
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
    public String getTokenHeaderValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(tokenType);
        sb.append(" ");
        sb.append(accessToken);
        return sb.toString();
    }
}