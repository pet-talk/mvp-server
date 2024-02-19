package petalk.mvp.application.auth.command.in;

import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.domain.auth.UserAuthority;

import java.util.UUID;

public interface RegisterSessionUsecase {

    void registerSession(RegisterSessionCommand command);

    class RegisterSessionCommand {
        private AuthUser.UserId userId;
        private UserAuthority userAuthority;

        private RegisterSessionCommand(AuthUser.UserId userId, UserAuthority userAuthority) {
            this.userId = userId;
            this.userAuthority = userAuthority;
        }

        public static RegisterSessionCommand from(UUID userId, String userAuthority) {
            return new RegisterSessionCommand(AuthUser.UserId.from(userId), UserAuthority.from(userAuthority));
        }

        public AuthUser.UserId getUserId() {
            return userId;
        }

        public UserAuthority getUserAuthority() {
            return userAuthority;
        }
    }
}
