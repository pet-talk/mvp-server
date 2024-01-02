package petalk.mvp.auth.application.command.out;

import jakarta.servlet.http.HttpServletRequest;
import petalk.mvp.auth.domain.SessionUserInfo;

/**
 * 세션을 등록하는 포트입니다.
 */
public interface RegisterSessionPort {

    void registerSession(SessionUserInfo user, HttpServletRequest httpServletRequest);
}
