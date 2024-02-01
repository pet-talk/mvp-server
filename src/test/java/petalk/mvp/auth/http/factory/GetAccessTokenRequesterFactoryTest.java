package petalk.mvp.auth.http.factory;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.domain.AuthorizationCode;
import petalk.mvp.auth.domain.SocialType;
import petalk.mvp.auth.http.model.GoogleTokenCommandBuilder;
import petalk.mvp.auth.http.model.NaverTokenCommandBuilder;
import petalk.mvp.auth.http.request.GetGoogleTokenRequester;
import petalk.mvp.auth.http.request.GetNaverTokenRequester;
import petalk.mvp.auth.http.request.GetSocialTokenRequester;
import petalk.mvp.core.annotation.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 소셜 액세스 토큰 http 요청자 팩토리에 대한 테스트 클래스입니다.
 */
@UnitTest
@DisplayName("소셜 액세스 토큰 http 요청자 팩토리 테스트")
class GetAccessTokenRequesterFactoryTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final String CLIENT_ID = "client_id";
    private final String REDIRECT_ID = "redirect_id";
    private final String CLIENT_SECRET = "client_secret";
    private final String GRANT_TYPE = "grant_type";
    private final String STATE = "state";
    private final String URL = "url";

    private final NaverTokenCommandBuilder tokenBuilder =
            new NaverTokenCommandBuilder(CLIENT_ID, REDIRECT_ID, CLIENT_SECRET, GRANT_TYPE, STATE);
    private final GoogleTokenCommandBuilder googleTokenCommandBuilder =
            new GoogleTokenCommandBuilder(CLIENT_ID, REDIRECT_ID, CLIENT_SECRET, GRANT_TYPE);
    private final GetNaverTokenRequester getNaverTokenRequester = new GetNaverTokenRequester(tokenBuilder, restTemplate, gson, URL);
    private final GetGoogleTokenRequester getGoogleTokenRequester = new GetGoogleTokenRequester(googleTokenCommandBuilder, restTemplate, gson, URL);
    private final GetAccessTokenRequesterFactory getAccessTokenRequesterFactory = new GetAccessTokenRequesterFactory(getNaverTokenRequester, getGoogleTokenRequester);

    /**
     * @given 소셜 인증 서비스가 네이버라면
     * @when 액세스 토큰 요청자를 가져올 때
     * @then 네이버 액세스 토큰 요청자를 반환한다.
     */
    @Test
    @DisplayName("소셜 타입이 네이버일 경우 네이버 액세스 토큰 요청자를 반환한다.")
    void whenTypeIsNaverThenReturnNaverRequester() {
        //given
        Authenticator naverAuthenticator = Authenticator.of(AuthorizationCode.from("code"), SocialType.NAVER);

        //when
        GetSocialTokenRequester oauthTokenRequester = getAccessTokenRequesterFactory.getOauthTokenRequester(naverAuthenticator);

        //then
        assertThat(oauthTokenRequester).isInstanceOf(GetNaverTokenRequester.class);
    }

}