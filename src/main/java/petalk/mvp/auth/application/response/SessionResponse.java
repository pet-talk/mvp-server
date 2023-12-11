package petalk.mvp.auth.application.response;

import petalk.mvp.auth.domain.Session;

import java.util.UUID;

/**
 * 세션을 나타내는 응답 모델입니다.
 */
public class SessionResponse {
    private Session.SessionId sessionId;

    private SessionResponse(Session.SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public static SessionResponse from(Session session) {
        return new SessionResponse(session.getSessionId());
    }

    public String getSessionId() {
        UUID value = sessionId.getValue();
        return value.toString();
    }
}
