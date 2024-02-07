package petalk.mvp.support.auth;

import java.util.UUID;

/**
 * 세션에 저장되어있는 유저 정보를 담고있는 클래스입니다.
 */
public class SessionUserModel {
    private UUID userId;
    private AuthRoleAuthority userAuthority;

    private SessionUserModel(UUID userId, AuthRoleAuthority userAuthority) {
        this.userId = userId;
        this.userAuthority = userAuthority;
    }

    public static SessionUserModel of(UUID userId, AuthRoleAuthority userAuthority) {
        return new SessionUserModel(userId, userAuthority);
    }

    public UUID getUserId() {
        return userId;
    }

    public AuthRoleAuthority getUserAuthority() {
        return userAuthority;
    }
}
