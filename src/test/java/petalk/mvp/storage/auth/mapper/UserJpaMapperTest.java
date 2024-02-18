package petalk.mvp.storage.auth.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.domain.auth.*;
import petalk.mvp.storage.postgre.model.UserJpa;
import petalk.mvp.core.annotation.UnitTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 이 클래스는 AuthUserJpaMapper 객체를 테스트합니다.
 */
@UnitTest
@DisplayName("AuthUserJpaMapper 단위 테스트")
class UserJpaMapperTest {

    private final UserJpaMapper userJpaMapper = new UserJpaMapper();

    /**
     * @given 사용자가 존재한다면
     * @when 사용자 도메인 모델을 영속성 모델로 변환할 때
     * @then 영속성 모델을 반환한다.
     */
    @Test
    @DisplayName("사용자 도메인 모델을 영속성 모델로 변환할 수 있다.")
    void mapToPersistenceModel() {
        //given
        UUID userId = UUID.randomUUID();
        String nickname = "nickname";
        LocalDateTime registrationDate = LocalDateTime.now();
        UserAuthority authorityType = UserAuthority.VET;

        AuthUser user = getExistUser(userId, nickname, registrationDate, authorityType);

        //when
        UserJpa userJpa = userJpaMapper.from(user);

        //then
        assertThat(userJpa)
                .isNotNull()
                .extracting("id", "nickname", "registrationDate", "authorityType")
                .containsExactly(userId, nickname, registrationDate, authorityType);
    }

    private static AuthUser getExistUser(UUID userId, String nickname, LocalDateTime registrationDate, UserAuthority authorityType) {
        UserSocialInfo userSocialInfo = UserSocialInfo.exist(UserSocialInfo.SocialInfoId.from(123L), "email", SocialType.NAVER, SocialAuthId.from("socialAuthId"), "name");

        return AuthUser.exist(AuthUser.UserId.from(userId), userSocialInfo, nickname, authorityType, registrationDate);
    }
}