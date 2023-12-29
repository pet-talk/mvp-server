package petalk.mvp.auth.application.response;

import petalk.mvp.auth.domain.Session;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.domain.UserAuthority;

import java.util.UUID;

/**
 * 세션에 저장할 유저 정보입니다.
 * 세션에 저장할 유저 정보는 유저의 아이디와 권한을 의미합니다.
 */
public class SessionUserResponse {

    private final User.UserId userId;
    private final String nickname;

    private final UserAuthority userAuthority;

    private SessionUserResponse(User.UserId userId, String nickname, UserAuthority userAuthority) {
        this.userId = userId;
        this.nickname = nickname;
        this.userAuthority = userAuthority;
    }

    public static SessionUserResponse from(Session session) {
        return new SessionUserResponse(session.getUserId(), session.getNickname(), session.getAuthority());
    }

    public UUID getUserId() {
        return userId.getValue();
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserAuthority() {
        return userAuthority.name();
    }
}
