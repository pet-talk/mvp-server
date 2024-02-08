package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.http.auth.adapter.SocialProfileKeyReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

/**
 * 네이버 토큰 요청을 테스트합니다.
 */
@UnitTest
@DisplayName("네이버 토큰 요청 유닛 테스트")
class NaverTokenRequesterTest {
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String CLIENT_ID = "client_id";
    private final String REDIRECT_ID = "redirect_id";
    private final String CLIENT_SECRET = "client_secret";
    private final String GRANT_TYPE = "grant_type";
    private final String STATE = "state";
    private final String URL = "url";

    private final NaverTokenCommandBuilder tokenBuilder =
            new NaverTokenCommandBuilder(CLIENT_ID, REDIRECT_ID, CLIENT_SECRET, GRANT_TYPE, STATE, URL);

    private final SocialProfileKeyReader keyReader = new NaverTokenRequester(tokenBuilder, restTemplate);

    /**
     * @given 코드를 요청 받고
     * @given 네이버에서 승인된 응답을 받는다면
     * @when 프로필 키를 요청할 때
     * @then 네이버 토큰 응답을 반환한다.
     */
    @Test
    @DisplayName("승인된 네이버 프로필 응답을 소셜 프로필로 변환한다.")
    void whenRequestNaverProfileThenReturnNaverProfile() throws Exception {
        //given
        AuthorizationCode code = AuthorizationCode.from("code");

        //given
        String token = "token";
        String tokenType = "bearer";
        String expiresIn = "3600";
        String refreshToken = "refresh";
        ResponseEntity<NaverTokenResponse> response = getSuccessResponse(token, tokenType, expiresIn, refreshToken);

        given(restTemplate.getForEntity(any(), eq(NaverTokenResponse.class)))
                .willReturn(response);

        //when
        Optional<SocialTokenResponse> result = keyReader.getKey(code);

        //then
        assertThat(result)
                .isPresent().get()
                .isInstanceOf(NaverTokenResponse.class)
                .extracting("accessToken", "refreshToken", "tokenType", "expiresIn")
                .containsExactly(token, refreshToken, tokenType, Integer.parseInt(expiresIn));
    }

    private ResponseEntity<NaverTokenResponse> getSuccessResponse(String token, String tokenType, String expiresIn, String refreshToken) throws JsonProcessingException {
        NaverTokenResponse response = new NaverTokenResponse(token, tokenType, refreshToken, expiresIn);

        return ResponseEntity.ok().body(response);
    }

    /**
     * @given 토큰을 받았고
     * @given 네이버에서 승인되지 않은 응답을 받는다면
     * @when 프로필 요청을 보내면
     * @then 빈 값을 반환한다.
     */
    @Test
    @DisplayName("승인되지 않은 네이버 프로필 응답은 빈 값을 반환한다.")
    void whenFailedThenReturnEmpty() throws Exception{
        //given
        AuthorizationCode code = AuthorizationCode.from("code");

        //given
        ResponseEntity<NaverTokenResponse> response = getFailedResponse();

        given(restTemplate.getForEntity(any(), eq(NaverTokenResponse.class)))
                .willReturn(response);

        //when
        Optional<SocialTokenResponse> result = keyReader.getKey(code);

        //then
        assertThat(result)
                .isEmpty();
    }

    private ResponseEntity<NaverTokenResponse> getFailedResponse() throws JsonProcessingException {

        NaverTokenResponse response = null;


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}