package petalk.mvp.auth.domain.command.out;

import petalk.mvp.auth.domain.command.User;

/**
 * 유저를 등록하는 포트입니다.
 */
public interface RegisterUserPort {

    void registerUser(User user);
}
