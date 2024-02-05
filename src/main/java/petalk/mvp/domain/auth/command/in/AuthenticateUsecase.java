package petalk.mvp.domain.auth.command.in;

import petalk.mvp.domain.auth.command.AuthenticateValidator;
import petalk.mvp.domain.auth.command.AuthorizationCode;
import petalk.mvp.domain.auth.response.AuthUserResponse;
import petalk.mvp.domain.auth.command.SocialType;
import petalk.mvp.domain.auth.command.User;

import java.util.UUID;

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



        public UUID getUserId() {
            return user.getUserId();
        }

        public String getNickname() {
            return user.getNickname();
        }

        public String getUserAuthority() {
            return user.getUserAuthority();
        }

    }

}