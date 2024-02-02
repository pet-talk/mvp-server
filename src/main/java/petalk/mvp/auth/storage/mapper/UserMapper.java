package petalk.mvp.auth.storage.mapper;

import petalk.mvp.auth.domain.command.User;
import petalk.mvp.auth.storage.model.AuthUserJpa;
import petalk.mvp.core.Mapper;

/**
 * 이 클래스는 User 객체를 매핑하는 작업을 담당합니다.
 */
@Mapper
public class UserMapper {

    public User from(AuthUserJpa userJpa) {
        return User.exist(User.UserId.from(userJpa.getId()), userJpa.getNickname(), userJpa.getAuthorityType(), userJpa.getRegistrationDate());
    }
}
