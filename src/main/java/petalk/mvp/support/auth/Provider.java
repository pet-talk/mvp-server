package petalk.mvp.support.auth;

import java.util.UUID;

/**
 * 요청한 유저 정보를 담고있는 클래스입니다.
 */
public class Provider {
    private UUID userId;
    private AuthRoleAuthority userAuthority;

    private Provider(UUID userId, AuthRoleAuthority userAuthority) {
        this.userId = userId;
        this.userAuthority = userAuthority;
    }

    public static Provider of(UUID userId, AuthRoleAuthority userAuthority) {
        return new Provider(userId, userAuthority);
    }

    public UUID getUserId() {
        return userId;
    }

    public AuthRoleAuthority getUserAuthority() {
        return userAuthority;
    }
}
