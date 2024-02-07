package petalk.mvp.application.auth.command.in;

import jakarta.servlet.http.HttpServletRequest;
import petalk.mvp.domain.auth.UserAuthority;
import petalk.mvp.domain.auth.AuthUser;

import java.util.UUID;

public interface RegisterSessionUsecase {

    void registerSession(RegisterSessionCommand command);

    class RegisterSessionCommand {
        private AuthUser.UserId userId;
        private UserAuthority userAuthority;
        private HttpServletRequest servletRequest;

        private RegisterSessionCommand(AuthUser.UserId userId, UserAuthority userAuthority, HttpServletRequest servletRequest) {
            this.userId = userId;
            this.userAuthority = userAuthority;
            this.servletRequest = servletRequest;
        }

        public static RegisterSessionCommand from(UUID userId, String userAuthority, HttpServletRequest servletRequest) {
            return new RegisterSessionCommand(AuthUser.UserId.from(userId), UserAuthority.from(userAuthority), servletRequest);
        }

        public AuthUser.UserId getUserId() {
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
