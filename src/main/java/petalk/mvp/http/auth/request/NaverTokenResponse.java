package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * 소셜 프로필 요청에 대한 응답을 나타냅니다.
 *
 * 응답에 대한 상태를 확인할 수 있습니다.
 */
public class NaverTokenResponse {
    private HttpStatusCode statusCode;
    private TokenInfo token;

    private NaverTokenResponse(TokenInfo token) {
        this.token = token;
    }

    public static NaverTokenResponse from(ResponseEntity<String> response, Gson gson) {
        TokenInfo tokenInfo = gson.fromJson(response.getBody(), TokenInfo.class);
        NaverTokenResponse profileResponse = new NaverTokenResponse(tokenInfo);
        profileResponse.addResponse(response);
        return profileResponse;
    }

    private void addResponse(ResponseEntity<String> response) {
        this.statusCode = response.getStatusCode();
    }

    public boolean isOk() {
        return statusCode.is2xxSuccessful();
    }

    public boolean isFailed() {
        return !isOk();
    }

    public Optional<AccessToken> mapToken() {
        if(isOk()) {
            return Optional.of(toAccessToken());
        }
        return Optional.empty();
    }

    private NaverAccessToken toAccessToken() {
        return new NaverAccessToken(token.accessToken, token.tokenType, token.refreshToken, token.expiresIn);
    }

    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    static class TokenInfo {
        @SerializedName("access_token")
        private String accessToken;
        @SerializedName("token_type")
        private String tokenType;
        @SerializedName("refresh_token")
        private String refreshToken;
        @SerializedName("expires_in")
        private String expiresIn;
    }
}
