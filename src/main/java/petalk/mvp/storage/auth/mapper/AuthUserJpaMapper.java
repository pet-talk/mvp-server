package petalk.mvp.storage.auth.mapper;

import petalk.mvp.domain.auth.User;
import petalk.mvp.storage.postgre.model.AuthUserJpa;
import petalk.mvp.core.Mapper;

/**
 * 이 클래스는 AuthUserJpa 객체를 매핑하는 작업을 담당합니다.
 */
@Mapper
public class AuthUserJpaMapper {

    public AuthUserJpa from(User user) {
        return new AuthUserJpa(user.getId().getValue(), user.getNickname(), user.getRegistrationDate(), user.getUserAuthority());
    }
}
