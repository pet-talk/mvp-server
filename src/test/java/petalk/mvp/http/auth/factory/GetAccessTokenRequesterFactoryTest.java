package petalk.mvp.http.auth.factory;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.adapter.GetAccessTokenRequesterFactory;
import petalk.mvp.http.auth.request.*;

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
    private final NaverTokenRequester getNaverTokenRequester = new NaverTokenRequester(tokenBuilder, restTemplate, gson, URL);
    private final GoogleTokenRequester getGoogleTokenRequester = new GoogleTokenRequester(googleTokenCommandBuilder, restTemplate, gson, URL);
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
        SocialType type = SocialType.NAVER;

        //when
        SocialTokenRequester oauthTokenRequester = getAccessTokenRequesterFactory.getOauthTokenRequester(type);

        //then
        assertThat(oauthTokenRequester.isCorrectType(type)).isTrue();
    }

    /**
     * @given 소셜 인증 서비스가 구글이라면
     * @when 액세스 토큰 요청자를 가져올 때
     * @then 구글 액세스 토큰 요청자를 반환한다.
     */
    @Test
    @DisplayName("소셜 타입이 구글일 경우 구글 액세스 토큰 요청자를 반환한다.")
    void whenTypeIsGoogleThenReturnGoogleRequester() {
        //given
        SocialType type = SocialType.GOOGLE;

        //when
        SocialTokenRequester oauthTokenRequester = getAccessTokenRequesterFactory.getOauthTokenRequester(type);

        //then
        assertThat(oauthTokenRequester.isCorrectType(type)).isTrue();
    }

}