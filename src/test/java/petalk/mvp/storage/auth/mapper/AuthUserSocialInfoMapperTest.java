package petalk.mvp.storage.auth.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.domain.auth.*;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

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
     * @given 사용자 도메인 모델이 존재한다면
     * @when 소셜 정보 도메인 모델을 영속성 모델로 변환할 때
     * @then 영속성 모델이 반환된다.
     */
    @Test
    @DisplayName("사용자와 소셜 사용자가 존재하면 소셜 정보 영속성 모델로 변환이 가능하다.")
    void WhenExistUserAndSocialUserThenCanMapPersistenceModel() {
        //given
        UUID uuid = UUID.randomUUID();
        Long socialInfoId = 213L;

        String socialAuthId = "id";
        String email = "email";
        String name = "name";

        AuthUser user = getExistUser(uuid, socialInfoId, socialAuthId, email, name);

        //when
        AuthUserSocialInfoJpa userSocialInfoJpa = authUserSocialInfoMapper.from(user);

        //then
        assertThat(userSocialInfoJpa)
                .isNotNull()
                .extracting("userId", "email", "socialId", "socialName")
                .containsExactly(uuid, email, socialAuthId, name);
    }

    private static AuthUser getExistUser(UUID uuid, Long socialInfoId, String socialAuthId, String email, String name) {
        UserAuthority authority = UserAuthority.VET;
        String nickname = "nickname";
        LocalDateTime registrationDate = LocalDateTime.now();
        UserSocialInfo userSocialInfo = UserSocialInfo.exist(UserSocialInfo.SocialInfoId.from(socialInfoId), email, SocialType.NAVER, SocialAuthId.from(socialAuthId), name);
        return AuthUser.exist(AuthUser.UserId.from(uuid), userSocialInfo, nickname, authority, registrationDate);
    }
}