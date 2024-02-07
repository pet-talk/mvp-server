package petalk.mvp.http.auth.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.http.auth.request.AccessToken;
import petalk.mvp.http.auth.request.NaverAccessToken;
import petalk.mvp.http.auth.request.NaverTokenResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 네이버 액세스 토큰 응답 모델을 테스트합니다.
 */
@UnitTest
@DisplayName("네이버 액세스 토큰 응답 유닛 테스트")
class NaverAccessTokenResponseTest {
    private final Gson gson = new Gson();

    /**
     * @given 네이버에서 승인된 응답을 받고
     * @given 네이버 액세스 토큰 응답으로 변환했다면
     * @when 액세스 토큰으로 변환했을 때
     * @then 네이버 액세스 토큰으로 반환한다.
     */
    @Test
    @DisplayName("승인된 네이버 액세스 토큰 응답을 소셜 프로필로 변환한다.")
    void whenRequestNaverTokenThenReturnNaverToken() {
        //given
        ResponseEntity<String> response = getSuccessResponse();

        //given
        NaverTokenResponse tokenResponse = NaverTokenResponse.from(response, gson);

        //when
        Optional<AccessToken> accessToken = tokenResponse.mapToken();

        //then
        assertThat(accessToken).isPresent()
                .get()
                .isInstanceOf(NaverAccessToken.class)
                .extracting("accessToken", "refreshToken", "tokenType", "expiresIn")
                .containsExactly(
                        "AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg",
                        "c8ceMEJisO4Se7uGisHoX0f5JEii7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY",
                        "bearer",
                        3600);
    }

    private static ResponseEntity<String> getSuccessResponse() {
        String response = "{\n" +
                "    \"access_token\":\"AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg\",\n" +
                "    \"refresh_token\":\"c8ceMEJisO4Se7uGisHoX0f5JEii7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY\",\n" +
                "    \"token_type\":\"bearer\",\n" +
                "    \"expires_in\":\"3600\"\n" +
                "}";

        return ResponseEntity.ok().body(response);
    }

    /**
     * @given 네이버에서 승인되지 않은 응답을 받고
     * @given 네이버 액세스 토큰 응답으로 변환했다면
     * @when 액세스 토큰으로 변환했을 때
     * @then 빈 값을 반환한다.
     */
    @Test
    @DisplayName("승인되지 않은 네이버 액세스 토큰 응답은 빈 값을 변환한다.")
    void whenFailedThenReturnEmpty() {
        //given
        ResponseEntity<String> response = getFailedResponse();

        //given
        NaverTokenResponse tokenResponse = NaverTokenResponse.from(response, gson);

        //when
        Optional<AccessToken> accessToken = tokenResponse.mapToken();

        //then
        assertThat(accessToken).isEmpty();
    }

    private static ResponseEntity<String> getFailedResponse() {
        String response =  "";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}