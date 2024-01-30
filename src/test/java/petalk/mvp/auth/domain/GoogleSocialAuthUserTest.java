package petalk.mvp.auth.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.core.annotation.UnitTest;


/**
 * 구글 소셜 유저의 도메인 테스트를 합니다.
 */
@UnitTest
@DisplayName("구글 소셜 유저의 도메인 단위 테스트")
class GoogleSocialAuthUserTest {

    /**
     * @when google 소셜 유저에서 닉네임을 가져올 때
     * @then 이름을 반환한다.
     */
    @Test
    @DisplayName("google 소셜 유저는 닉네임이 필요할 때 이름을 반환한다.")
    void dfd() {
        //when
        String name = "name";
        SocialAuthUser user = GoogleSocialAuthUser.from(SocialAuthId.from("id"), "email", name);

        //then
        Assertions.assertThat(user.getNickname()).isEqualTo(name);
    }

}