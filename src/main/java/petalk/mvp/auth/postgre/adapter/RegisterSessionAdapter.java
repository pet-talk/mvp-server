package petalk.mvp.auth.postgre.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.application.command.out.RegisterSessionPort;
import petalk.mvp.auth.domain.Session;
import petalk.mvp.auth.postgre.mapper.SessionModelMapper;
import petalk.mvp.auth.postgre.repository.SessionRepository;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.core.auth.SessionModel;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterSessionAdapter implements RegisterSessionPort {

    private final SessionRepository sessionRepository;
    private final SessionModelMapper sessionModelMapper;

    @Override
    public void registerSession(Session session) {
        SessionModel sessionModel = sessionModelMapper.from(session);
        sessionRepository.save(sessionModel);
    }
}
