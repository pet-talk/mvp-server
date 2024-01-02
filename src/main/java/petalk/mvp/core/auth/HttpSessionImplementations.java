package petalk.mvp.core.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class HttpSessionImplementations {

    private final String HTTP_SESSION_KEY = "session";

    public void save(SessionUserModel sessionModel, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(HTTP_SESSION_KEY, sessionModel);
    }
}
