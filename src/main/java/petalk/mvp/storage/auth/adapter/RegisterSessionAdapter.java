package petalk.mvp.storage.auth.adapter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import petalk.mvp.domain.auth.command.out.RegisterSessionPort;
import petalk.mvp.domain.auth.command.SessionUserInfo;
import petalk.mvp.storage.postgre.SessionRepository;
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
