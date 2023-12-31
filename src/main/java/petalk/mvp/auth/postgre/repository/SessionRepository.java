package petalk.mvp.auth.postgre.repository;

import jakarta.servlet.http.HttpServletRequest;
import petalk.mvp.core.auth.SessionUserModel;

public interface SessionRepository {

    void save(SessionUserModel sessionModel, HttpServletRequest httpServletRequest);
}
