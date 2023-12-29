package petalk.mvp.auth.postgre.mapper;

import petalk.mvp.auth.domain.Session;
import petalk.mvp.core.Mapper;
import petalk.mvp.core.auth.SessionAuthority;
import petalk.mvp.core.auth.SessionModel;

@Mapper
public class SessionModelMapper {

    public SessionModel from(Session session) {
        SessionAuthority sessionAuthority = SessionAuthority.from(session.getAuthority());
        return SessionModel.of(session.getSessionId().getValue(), session.getRegistrationDate(), session.getUserId().getValue(), sessionAuthority);
    }
}
