package petalk.mvp.auth.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.common.annotation.UnitTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 소셜 인증 관리자에 대한 단위 테스트를 진행합니다.
 */
@UnitTest
@DisplayName("소셜 인증 관리자에 대한 도메인 단위 테스트")
class SocialAuthenticateManagerTest {

    /**
     * @given 소셜 인증 관리자가 존재한다면
     * @when 네이버 소셜 인증 요청을 할 때
     * @then 네이버 소셜 인증자를 반환한다.
     */
    @Test
    @DisplayName("네이버 소셜 인증 요청을 할 때 네이버 소셜 인증자를 반환한다.")
    void returnNaverSocialAuthenticator() {
        //given
        SocialAuthenticateManager manager = SocialAuthenticateManager.getInstance();
        //when
        SocialAuthenticator authenticator = manager.getSocialAuthenticator(SocialType.NAVER, AccessToken.from("token"));
        //then
        Assertions.assertThat(authenticator).isInstanceOf(NaverAuthenticator.class);
    }
}