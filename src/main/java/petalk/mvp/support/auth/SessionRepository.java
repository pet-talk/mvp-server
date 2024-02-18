package petalk.mvp.support.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface SessionRepository {

    void save(Provider sessionModel, HttpServletRequest httpServletRequest);
}
