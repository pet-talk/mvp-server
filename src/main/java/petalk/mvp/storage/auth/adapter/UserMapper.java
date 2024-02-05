package petalk.mvp.storage.auth.adapter;

import petalk.mvp.domain.auth.command.User;
import petalk.mvp.storage.postgre.model.AuthUserJpa;
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
