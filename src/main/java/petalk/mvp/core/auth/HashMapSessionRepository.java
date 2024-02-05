package petalk.mvp.core.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import petalk.mvp.storage.postgre.SessionRepository;

@Component
@RequiredArgsConstructor
public class HashMapSessionRepository implements SessionRepository {
    private final String HTTP_SESSION_KEY = "session";
    @Override
    public void save(SessionUserModel model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(HTTP_SESSION_KEY, model);
    }
}
