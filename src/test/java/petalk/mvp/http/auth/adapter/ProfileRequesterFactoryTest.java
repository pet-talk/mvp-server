package petalk.mvp.http.auth.adapter;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.request.GoogleProfileRequester;
import petalk.mvp.http.auth.request.NaverProfileRequester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 소셜 프로필 http 요청자 팩토리에 대한 테스트 클래스입니다.
 */
@UnitTest
@DisplayName("소셜 프로필 http 요청자 팩토리 테스트")
class ProfileRequesterFactoryTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final String URL = "url";
    private final NaverProfileRequester getNaverProfileRequester = new NaverProfileRequester(restTemplate, URL);
    private final GoogleProfileRequester getGoogleProfileRequester = new GoogleProfileRequester(restTemplate, gson, URL);
    private final ProfileRequesterFactory profileRequesterFactory = new ProfileRequesterFactory(getNaverProfileRequester, getGoogleProfileRequester);


    /**
     * @given 소셜 인증 서비스가 네이버라면
     * @when 프로필 요청자를 가져올 때
     * @then 네이버 프로필 요청자를 반환한다.
     */
    @Test
    @DisplayName("소셜 타입이 네이버일 경우 네이버 프로필 요청자를 반환한다.")
    void whenTypeIsNaverThenReturnNaverRequester() {
        //given
        SocialType type = SocialType.NAVER;

        //when
        SocialProfileReader oauthTokenRequester = profileRequesterFactory.getProfileRequester(type);

        //then
        assertThat(oauthTokenRequester.isCorrectType(type)).isTrue();
    }

}