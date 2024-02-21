package petalk.mvp.support.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class HttpSessionRepository implements SessionRepository {
    private final String HTTP_SESSION_KEY = "session";
    @Override
    public void save(Provider model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(HTTP_SESSION_KEY, model);
    }

    @Override
    public Optional<Provider> getProvider(String sessionId) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();

        Provider attribute = (Provider) session.getAttribute(HTTP_SESSION_KEY);
        if (attribute == null) {
            return Optional.empty();
        }
        return Optional.of(attribute);
    }
}
