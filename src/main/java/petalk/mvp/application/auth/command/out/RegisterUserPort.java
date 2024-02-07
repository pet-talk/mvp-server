package petalk.mvp.application.auth.command.out;

import petalk.mvp.domain.auth.User;

/**
 * 유저를 등록하는 포트입니다.
 */
public interface RegisterUserPort {

    void registerUser(User user);
}
