package petalk.mvp.auth.http.factory;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.domain.AuthorizationCode;
import petalk.mvp.auth.domain.SocialType;
import petalk.mvp.auth.http.request.GetNaverProfileRequester;
import petalk.mvp.auth.http.request.GetSocialProfileRequester;
import petalk.mvp.core.annotation.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 소셜 프로필 http 요청자 팩토리에 대한 테스트 클래스입니다.
 */
@UnitTest
@DisplayName("소셜 프로필 http 요청자 팩토리 테스트")
class GetProfileRequesterFactoryTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final String URL = "url";
    private final GetNaverProfileRequester getNaverProfileRequester = new GetNaverProfileRequester(restTemplate, gson, URL);
    private final GetProfileRequesterFactory getProfileRequesterFactory = new GetProfileRequesterFactory(getNaverProfileRequester);


    /**
     * @given 소셜 인증 서비스가 네이버라면
     * @when 프로필 요청자를 가져올 때
     * @then 네이버 프로필 요청자를 반환한다.
     */
    @Test
    @DisplayName("소셜 타입이 네이버일 경우 네이버 프로필 요청자를 반환한다.")
    void whenTypeIsNaverThenReturnNaverRequester() {
        //given
        Authenticator naverAuthenticator = Authenticator.of(AuthorizationCode.from("code"), SocialType.NAVER);

        //when
        GetSocialProfileRequester oauthTokenRequester = getProfileRequesterFactory.getProfileRequester(naverAuthenticator);

        //then
        assertThat(oauthTokenRequester).isInstanceOf(GetNaverProfileRequester.class);
    }

}