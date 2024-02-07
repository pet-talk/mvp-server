package petalk.mvp.application.auth.response;

import petalk.mvp.domain.auth.User;

import java.util.UUID;

/**
 * 세션에 저장할 유저 정보입니다.
 * 세션에 저장할 유저 정보는 유저의 아이디와 권한을 의미합니다.
 */
public class AuthUserResponse {

    private final UUID userId;
    private final String nickname;
    private final String userAuthority;

    private AuthUserResponse(UUID userId, String nickname, String userAuthority) {
        this.userId = userId;
        this.nickname = nickname;
        this.userAuthority = userAuthority;
    }

    public static AuthUserResponse from(User user) {
        return new AuthUserResponse(user.getId().getValue(), user.getNickname(), user.getUserAuthority().name());
    }

    public UUID getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserAuthority() {
        return userAuthority;
    }
}
