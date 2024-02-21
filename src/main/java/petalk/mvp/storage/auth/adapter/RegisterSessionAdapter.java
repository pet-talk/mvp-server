package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.application.auth.command.out.RegisterSessionPort;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.domain.auth.SessionUserInfo;
import petalk.mvp.storage.auth.mapper.SessionUserModelMapper;
import petalk.mvp.support.auth.Provider;
import petalk.mvp.support.auth.SessionRepository;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterSessionAdapter implements RegisterSessionPort {

    private final SessionRepository sessionRepository;
    private final SessionUserModelMapper mapper;

    @Override
    public void registerSession(SessionUserInfo user) {
        Provider model = mapper.from(user);
        sessionRepository.save(model);
    }
}
