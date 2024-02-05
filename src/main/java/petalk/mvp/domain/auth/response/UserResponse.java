package petalk.mvp.domain.auth.response;

import petalk.mvp.domain.auth.command.User;

import java.util.UUID;

/**
 * 유저를 나타내는 응답 모델입니다.
 */
public class UserResponse {
    private User.UserId userId;

    private UserResponse(User.UserId userId) {
        this.userId = userId;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId());
    }

    public String getUserId() {
        UUID value = userId.getValue();
        return value.toString();
    }
}
