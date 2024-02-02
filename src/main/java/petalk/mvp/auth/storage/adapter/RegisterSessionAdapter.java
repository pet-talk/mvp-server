package petalk.mvp.auth.storage.adapter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.domain.command.out.RegisterSessionPort;
import petalk.mvp.auth.domain.command.SessionUserInfo;
import petalk.mvp.auth.storage.mapper.SessionUserModelMapper;
import petalk.mvp.auth.storage.repository.SessionRepository;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.core.auth.SessionUserModel;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterSessionAdapter implements RegisterSessionPort {

    private final SessionRepository sessionRepository;
    private final SessionUserModelMapper mapper;

    @Override
    public void registerSession(SessionUserInfo user, HttpServletRequest httpServletRequest) {
        SessionUserModel model = mapper.from(user);
        sessionRepository.save(model, httpServletRequest);
    }
}
