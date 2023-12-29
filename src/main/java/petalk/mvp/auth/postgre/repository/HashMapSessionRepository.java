package petalk.mvp.auth.postgre.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import petalk.mvp.core.auth.SessionDBImplementations;
import petalk.mvp.core.auth.SessionModel;

@Component
@RequiredArgsConstructor
public class HashMapSessionRepository implements SessionRepository {

    private final SessionDBImplementations implementations;
    @Override
    public void save(SessionModel sessionModel) {
        implementations.save(sessionModel);
    }

}
