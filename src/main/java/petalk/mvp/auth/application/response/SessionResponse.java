package petalk.mvp.auth.application.response;

import petalk.mvp.auth.domain.Session;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 세션을 나타내는 응답 모델입니다.
 */
public class SessionResponse {
    private Session.SessionId sessionId;
    private LocalDateTime registrationDate;
    private SessionUserResponse user;

    public SessionResponse(Session.SessionId sessionId, LocalDateTime registrationDate, SessionUserResponse user) {
        this.sessionId = sessionId;
        this.registrationDate = registrationDate;
        this.user = user;
    }

    public static SessionResponse from(Session session) {
        return new SessionResponse(session.getSessionId(), session.getRegistrationDate(), SessionUserResponse.from(session));
    }

    public UUID getSessionId() {
        return sessionId.getValue();
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public UUID getUserId() {
        return user.getUserId();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public String getUserAuthority() {
        return user.getUserAuthority();
    }
}
