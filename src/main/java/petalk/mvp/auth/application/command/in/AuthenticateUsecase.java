package petalk.mvp.auth.application.command.in;

import petalk.mvp.auth.application.command.validator.AuthenticateValidator;
import petalk.mvp.auth.application.response.SessionResponse;
import petalk.mvp.auth.domain.AuthorizationCode;
import petalk.mvp.auth.domain.Session;
import petalk.mvp.auth.domain.SocialType;

/**
 * 인증을 담당하는 유스케이스입니다.
 */
public interface AuthenticateUsecase {

    /**
     * 소셜 인증을 합니다.
     * @param command 인증에 필요한 요청 정보
     * @return 인증된 유저의 권한
     */
    AuthenticateResponse authenticate(AuthenticateCommand command);

    class AuthenticateCommand {
        private AuthorizationCode token;
        private SocialType socialType;

        private AuthenticateCommand(AuthorizationCode token, SocialType socialType) {
            this.token = token;
            this.socialType = socialType;
        }

        public static AuthenticateCommand from(String tokenValue, String socialTypeName, AuthenticateValidator validator) {
            AuthorizationCode token = AuthorizationCode.from(tokenValue);
            SocialType socialType = SocialType.from(socialTypeName);

            AuthenticateCommand authenticateCommand = new AuthenticateCommand(token, socialType);

            validator.validate(authenticateCommand);

            return authenticateCommand;
        }

        public AuthorizationCode getToken() {
            return token;
        }

        public SocialType getSocialType() {
            return socialType;
        }

    }

    class AuthenticateResponse {
        private SessionResponse session;

        private AuthenticateResponse(SessionResponse session) {
            this.session = session;
        }

        public static AuthenticateResponse from(Session session) {
            return new AuthenticateResponse(SessionResponse.from(session));
        }

        public SessionResponse getSession() {
            return session;
        }

    }

}
