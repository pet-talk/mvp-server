package petalk.mvp.application.auth.response;

import petalk.mvp.domain.auth.AuthUser;

import java.util.UUID;

/**
 * 유저를 나타내는 응답 모델입니다.
 */
public class UserResponse {
    private AuthUser.UserId userId;

    private UserResponse(AuthUser.UserId userId) {
        this.userId = userId;
    }

    private UserResponse() {}

    public static UserResponse from(AuthUser user) {
        return new UserResponse(user.getId());
    }

    public String getUserId() {
        UUID value = userId.getValue();
        return value.toString();
    }
}
