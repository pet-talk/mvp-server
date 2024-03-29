package petalk.mvp.storage.auth.mapper;

import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.storage.postgre.model.UserJpa;
import petalk.mvp.core.Mapper;

/**
 * 이 클래스는 AuthUserJpa 객체를 매핑하는 작업을 담당합니다.
 */
@Mapper
public class UserJpaMapper {

    public UserJpa from(AuthUser user) {
        return new UserJpa(user.getId().getValue(), user.getNickname(), user.getRegistrationDate(), user.getUserAuthority());
    }
}
