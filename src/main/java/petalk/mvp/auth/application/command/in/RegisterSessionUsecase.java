package petalk.mvp.auth.application.command.in;

import jakarta.servlet.http.HttpServletRequest;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.domain.UserAuthority;

import java.util.UUID;

public interface RegisterSessionUsecase {

    void registerSession(RegisterSessionCommand command);

    class RegisterSessionCommand {
        private User.UserId userId;
        private UserAuthority userAuthority;
        private HttpServletRequest servletRequest;

        private RegisterSessionCommand(User.UserId userId, UserAuthority userAuthority, HttpServletRequest servletRequest) {
            this.userId = userId;
            this.userAuthority = userAuthority;
            this.servletRequest = servletRequest;
        }

        public static RegisterSessionCommand from(UUID userId, String userAuthority, HttpServletRequest servletRequest) {
            return new RegisterSessionCommand(User.UserId.from(userId), UserAuthority.from(userAuthority), servletRequest);
        }

        public User.UserId getUserId() {
            return userId;
        }

        public UserAuthority getUserAuthority() {
            return userAuthority;
        }

        public HttpServletRequest getServletRequest() {
            return servletRequest;
        }
    }
}
