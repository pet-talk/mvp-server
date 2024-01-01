package petalk.mvp.auth.application.command.in;

import petalk.mvp.auth.application.command.validator.AuthenticateValidator;
import petalk.mvp.auth.application.response.AuthUserResponse;
import petalk.mvp.auth.domain.AuthorizationCode;
import petalk.mvp.auth.domain.SocialType;
import petalk.mvp.auth.domain.User;

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
        private AuthorizationCode code;
        private SocialType socialType;

        private AuthenticateCommand(AuthorizationCode code, SocialType socialType) {
            this.code = code;
            this.socialType = socialType;
        }

        public static AuthenticateCommand from(String tokenValue, String socialTypeName, AuthenticateValidator validator) {
            AuthorizationCode token = AuthorizationCode.from(tokenValue);
            SocialType socialType = SocialType.from(socialTypeName);

            AuthenticateCommand authenticateCommand = new AuthenticateCommand(token, socialType);

            validator.validate(authenticateCommand);

            return authenticateCommand;
        }

        public AuthorizationCode getCode() {
            return code;
        }

        public SocialType getSocialType() {
            return socialType;
        }

    }

    class AuthenticateResponse {
        private AuthUserResponse user;

        private AuthenticateResponse(AuthUserResponse user) {
            this.user = user;
        }

        public static AuthenticateResponse from(User user) {
            return new AuthenticateResponse(AuthUserResponse.from(user));
        }

        public AuthUserResponse getUser() {
            return user;
        }

    }

}
