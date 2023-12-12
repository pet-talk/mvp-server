package petalk.mvp.auth.application.response;

import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.domain.UserAuthority;

import java.util.UUID;

/**
 * 세션에 저장할 유저 정보입니다.
 * 세션에 저장할 유저 정보는 유저의 아이디와 권한을 의미합니다.
 */
public class SessionUserResponse {

    private final User.UserId userId;

    private final UserAuthority userAuthority;

    private SessionUserResponse(User.UserId userId, UserAuthority userAuthority) {
        this.userId = userId;
        this.userAuthority = userAuthority;
    }

    public static SessionUserResponse from(User user) {
        return new SessionUserResponse(user.getId(), user.getUserAuthority());
    }

    public String getUserId() {
        UUID value = userId.getValue();
        return value.toString();
    }

    public String getUserAuthority() {
        return userAuthority.name();
    }
}
