package petalk.mvp.core.auth;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 세션 정보를 담고있는 클래스입니다.
 *
 *
 */
public class SessionModel {
    private static final int SESSION_EXPIRATION_HOURS = 24;

    private UUID sessionId;
    private SessionUserModel sessionUserModel;

    private LocalDateTime registrationDate;

    private SessionModel(UUID sessionId, LocalDateTime registrationDate, SessionUserModel sessionUserModel) {
        this.sessionId = sessionId;
        this.sessionUserModel = sessionUserModel;
        this.registrationDate = registrationDate;
    }

    public static SessionModel of(UUID sessionId, LocalDateTime registrationDate, UUID userId, SessionAuthority userAuthority) {
        SessionUserModel userModel = SessionUserModel.of(userId, userAuthority);
        return new SessionModel(sessionId, registrationDate, userModel);
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    public UUID getUserId() {
        return sessionUserModel.getUserId();
    }

    public SessionAuthority getUserAuthority() {
        return sessionUserModel.getUserAuthority();
    }

    public boolean isExpired(LocalDateTime now) {
        return !registrationDate.plusHours(SESSION_EXPIRATION_HOURS).isBefore(now);
    }

}
