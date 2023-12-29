package petalk.mvp.auth.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 세션을 나타내는 클래스입니다.
 *
 */
public class Session {

    /**
     * 세션 아이디입니다.
     * 해당 클래스는 sesseionId를 통해 같은 클래스임을 비교합니다.
     *
     * @see #equals(Object)
     * @see #hashCode()
     */
    private SessionId sessionId;
    private LocalDateTime registrationDate;
    private SessionUserInfo sessionUserInfo;

    //== 생성 메소드 ==//
    private Session(SessionId sessionId, LocalDateTime registrationDate, SessionUserInfo sessionUserInfo) {
        this.sessionId = sessionId;
        this.registrationDate = registrationDate;
        this.sessionUserInfo = sessionUserInfo;
    }

    public static Session register(User user, LocalDateTime now) {
        return new Session(SessionId.register(), now, SessionUserInfo.register(user));
    }
    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    public SessionId getSessionId() {
        return sessionId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public User.UserId getUserId() {
        return sessionUserInfo.getId();
    }
    public String getNickname() {
        return sessionUserInfo.getNickname();
    }

    public UserAuthority getAuthority() {
        return sessionUserInfo.getAuthority();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session that = (Session) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }


    /**
     * 세션의 id를 나타내는 클래스입니다.
     */
    public static class SessionId {

        private UUID id;

        private SessionId(UUID id) {
            this.id = id;
        }

        public static SessionId from(UUID id) {
            return new SessionId(id);
        }

        public static SessionId register() {
            UUID uuid = UUID.randomUUID();
            return SessionId.from(uuid);
        }

        public UUID getValue() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SessionId sessionId = (SessionId) o;
            return Objects.equals(id, sessionId.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
