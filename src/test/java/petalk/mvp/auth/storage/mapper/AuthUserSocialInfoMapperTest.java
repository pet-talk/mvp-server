package petalk.mvp.auth.storage.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.auth.domain.command.*;
import petalk.mvp.auth.storage.model.AuthUserSocialInfoJpa;
import petalk.mvp.core.annotation.UnitTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 이 클래스는 AuthUserSocialInfoMapper 객체를 테스트합니다.
 */
@UnitTest
@DisplayName("AuthUserSocialInfoMapper 단위 테스트")
class AuthUserSocialInfoMapperTest {

    private final AuthUserSocialInfoMapper authUserSocialInfoMapper = new AuthUserSocialInfoMapper();

    /**
     * @given 사용자 도메인이 존재하고
     * @given 소셜 사용자 도메인 모델이 존재한다면
     * @when 소셜 정보 도메인 모델을 영속성 모델로 변환할 때
     * @then 영속성 모델이 반환된다.
     */
    @Test
    @DisplayName("사용자와 소셜 사용자가 존재하면 소셜 정보 영속성 모델로 변환이 가능하다.")
    void WhenExistUserAndSocialUserThenCanMapPersistenceModel() {
        //given
        User user = getExistUser();

        //given
        SocialAuthUser authUser = createNaverSocialUser();

        //when
        UserSocialInfo userSocialInfo = UserSocialInfo.register(user, authUser);
        AuthUserSocialInfoJpa userSocialInfoJpa = authUserSocialInfoMapper.from(userSocialInfo);

        //then
        assertThat(userSocialInfoJpa)
                .isNotNull()
                .extracting("userId", "email", "socialType", "socialId", "socialName")
                .containsExactly(user.getId().getValue(), authUser.getEmail(), authUser.getSocialType(), authUser.getSocialId().getValue(), authUser.getName());
    }

    private static SocialAuthUser createNaverSocialUser() {
        SocialAuthId id = SocialAuthId.from("id");
        String email = "email";
        String name = "name";
        String nickname = "nickname";
        return NaverSocialAuthUser.from(id, email, nickname, name);
    }

    private static User getExistUser() {
        UUID uuid = UUID.randomUUID();
        UserAuthority authority = UserAuthority.VET;
        String nickname = "nickname";
        LocalDateTime registrationDate = LocalDateTime.now();
        return User.exist(User.UserId.from(uuid), nickname, authority, registrationDate);
    }
}