package petalk.mvp.auth.storage.repository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import petalk.mvp.core.auth.HttpSessionImplementations;
import petalk.mvp.core.auth.SessionUserModel;

@Component
@RequiredArgsConstructor
public class HashMapSessionRepository implements SessionRepository {

    private final HttpSessionImplementations httpSessionImplementations;
    @Override
    public void save(SessionUserModel model, HttpServletRequest httpServletRequest) {
        httpSessionImplementations.save(model, httpServletRequest);
    }
}
