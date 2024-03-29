package petalk.mvp.application.auth.response;

import petalk.mvp.domain.auth.AuthUser;

import java.util.UUID;

/**
 * 세션에 저장할 유저 정보입니다.
 * 세션에 저장할 유저 정보는 유저의 아이디와 권한을 의미합니다.
 */
public class AuthUserResponse {

    private UUID userId;
    private String nickname;
    private String userAuthority;

    private AuthUserResponse() {}

    private AuthUserResponse(UUID userId, String nickname, String userAuthority) {
        this.userId = userId;
        this.nickname = nickname;
        this.userAuthority = userAuthority;
    }

    public static AuthUserResponse from(AuthUser user) {
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
