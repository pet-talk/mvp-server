package petalk.mvp.storage.auth.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.domain.auth.User;
import petalk.mvp.domain.auth.UserAuthority;
import petalk.mvp.storage.auth.mapper.AuthUserJpaMapper;
import petalk.mvp.storage.postgre.model.AuthUserJpa;
import petalk.mvp.core.annotation.UnitTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 이 클래스는 AuthUserJpaMapper 객체를 테스트합니다.
 */
@UnitTest
@DisplayName("AuthUserJpaMapper 단위 테스트")
class AuthUserJpaMapperTest {

    private final AuthUserJpaMapper authUserJpaMapper = new AuthUserJpaMapper();

    /**
     * @given 사용자가 존재한다면
     * @when 사용자 도메인 모델을 영속성 모델로 변환할 때
     * @then 영속성 모델을 반환한다.
     */
    @Test
    @DisplayName("사용자 도메인 모델을 영속성 모델로 변환할 수 있다.")
    void mapToPersistenceModel() {
        //given
        User user = getExistUser();

        //when
        AuthUserJpa authUserJpa = authUserJpaMapper.from(user);

        //then
        assertThat(authUserJpa)
                .isNotNull()
                .extracting("id", "nickname", "registrationDate", "authorityType")
                .containsExactly(user.getId().getValue(), user.getNickname(), user.getRegistrationDate(), user.getUserAuthority());
    }

    private static User getExistUser() {
        UUID uuid = UUID.randomUUID();
        UserAuthority authority = UserAuthority.VET;
        String nickname = "nickname";
        LocalDateTime registrationDate = LocalDateTime.now();
        return User.exist(User.UserId.from(uuid), nickname, authority, registrationDate);
    }
}