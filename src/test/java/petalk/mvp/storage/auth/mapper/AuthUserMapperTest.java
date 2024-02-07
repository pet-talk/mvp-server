package petalk.mvp.storage.auth.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.domain.auth.UserAuthority;
import petalk.mvp.storage.postgre.model.AuthUserJpa;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 이 클래스는 UserMapper 객체를 테스트합니다.
 */
@UnitTest
@DisplayName("UserMapper 단위 테스트")
class AuthUserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    /**
     * @given 저장된 사용자 데이터가 존재한다면
     * @when 사용자 데이터를 사용자 도메인으로 변환할 때
     * @then 사용자 도메인을 반환한다.
     */
    @Test
    @DisplayName("사용자 데이터를 사용자 도메인으로 변환한다.")
    void mapToUser() {
        //given
        UUID uuid = UUID.randomUUID();
        LocalDateTime registrationDate = LocalDateTime.now();
        UserAuthority authorityType = UserAuthority.VET;

        String email = "email";
        String name = "name";
        SocialType socialType = SocialType.NAVER;
        String socialId = "id";

        AuthUserJpa userJpa = createUserJpa(uuid, registrationDate, authorityType);

        AuthUserSocialInfoJpa socialInfoJpa = new AuthUserSocialInfoJpa(uuid, email, socialType, socialId, name);

        //when
        AuthUser user = userMapper.from(userJpa, socialInfoJpa);

        //then
        assertThat(user)
                .isNotNull()
                .extracting("id.id", "authorities.authority", "registrationDate", "socialInfo.email", "socialInfo.socialType", "socialInfo.socialId.id", "socialInfo.socialName")
                .containsExactly(uuid, authorityType, registrationDate, email, socialType, socialId, name);
    }

    private static AuthUserJpa createUserJpa(UUID uuid, LocalDateTime registrationDate, UserAuthority authorityType) {
        String nickname = "vet";
        return new AuthUserJpa(uuid, nickname, registrationDate, authorityType);
    }
}