package petalk.mvp.storage.postgre;

import jakarta.servlet.http.HttpServletRequest;
import petalk.mvp.support.auth.SessionUserModel;

public interface SessionRepository {

    void save(SessionUserModel sessionModel, HttpServletRequest httpServletRequest);
}
