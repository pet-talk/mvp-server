package petalk.mvp.auth.http.model;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * 소셜 프로필 요청에 대한 응답을 나타냅니다.
 *
 * 응답에 대한 상태를 확인할 수 있습니다.
 */
public class NaverTokenResponse {

    private final ResponseEntity<String> response;

    private NaverTokenResponse(ResponseEntity<String> response) {
        this.response = response;
    }

    public static NaverTokenResponse from(ResponseEntity<String> response) {
        return new NaverTokenResponse(response);
    }

    public boolean isOk() {
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean isFailed() {
        return !response.getStatusCode().is2xxSuccessful();
    }

    public Optional<AccessToken> mapToken(Gson gson) {
        if(isOk()) {
            return Optional.of(toAccessToken(gson));
        }
        return Optional.empty();
    }

    private NaverAccessToken toAccessToken(Gson gson) {
        return gson.fromJson(response.getBody(), NaverAccessToken.class);
    }
}
