package petalk.mvp.storage.auth.adapter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import petalk.mvp.application.auth.command.out.RegisterSessionPort;
import petalk.mvp.domain.auth.SessionUserInfo;
import petalk.mvp.storage.auth.mapper.SessionUserModelMapper;
import petalk.mvp.support.auth.SessionRepository;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.support.auth.Provider;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterSessionAdapter implements RegisterSessionPort {

    private final SessionRepository sessionRepository;
    private final SessionUserModelMapper mapper;

    @Override
    public void registerSession(SessionUserInfo user, HttpServletRequest httpServletRequest) {
        Provider model = mapper.from(user);
        sessionRepository.save(model, httpServletRequest);
    }
}
