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