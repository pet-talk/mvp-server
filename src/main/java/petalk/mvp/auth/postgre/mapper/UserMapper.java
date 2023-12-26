package petalk.mvp.auth.postgre.mapper;

import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.postgre.model.AuthUserJpa;
import petalk.mvp.core.Mapper;

/**
 * 이 클래스는 User 객체를 매핑하는 작업을 담당합니다.
 */
@Mapper
public class UserMapper {

    public User from(AuthUserJpa userJpa) {
        return User.exist(userJpa.getId(), userJpa.getAuthorityType(), userJpa.getRegistrationDate());
    }
}
