package petalk.mvp.auth.postgre.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.domain.UserAuthority;
import petalk.mvp.auth.postgre.model.AuthUserJpa;
import petalk.mvp.core.annotation.UnitTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@DisplayName("UserMapper 단위 테스트")
class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    /**
     * @given 저장된 사용자 데이터가 존재한다면
     * @when 사용자 데이터를 사용자 도메인으로 변환한다면
     * @then 사용자 도메인을 반환한다.
     */
    @Test
    @DisplayName("사용자 데이터를 사용자 도메인으로 변환한다.")
    void mapToUser() {
        //given
        AuthUserJpa userJpa = createUserJpa();

        //when
        User user = userMapper.from(userJpa);

        //then
        assertThat(user)
                .isNotNull()
                .extracting("id.id", "authorities.authority", "registrationDate")
                .containsExactly(userJpa.getId(), userJpa.getAuthorityType(), userJpa.getRegistrationDate());
    }

    private static AuthUserJpa createUserJpa() {
        UUID uuid = UUID.randomUUID();
        String nickname = "vet";
        LocalDateTime registrationDate = LocalDateTime.now();
        UserAuthority authorityType = UserAuthority.VET;
        AuthUserJpa userJpa = new AuthUserJpa(uuid, nickname, registrationDate, authorityType);
        return userJpa;
    }
}