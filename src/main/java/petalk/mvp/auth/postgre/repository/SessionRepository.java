package petalk.mvp.auth.postgre.repository;

import petalk.mvp.core.auth.SessionModel;

public interface SessionRepository {

    void save(SessionModel sessionModel);
}
