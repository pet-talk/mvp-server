package petalk.mvp.auth.application.command.out;

import petalk.mvp.auth.domain.Session;
import petalk.mvp.auth.domain.User;

/**
 * 세션을 등록하는 포트입니다.
 */
public interface RegisterSessionPort {

    void registerSession(Session session, User user);
}
