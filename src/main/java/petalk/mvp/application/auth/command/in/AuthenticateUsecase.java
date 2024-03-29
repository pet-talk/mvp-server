package petalk.mvp.application.auth.command.in;

import petalk.mvp.application.auth.command.validator.AuthenticateValidator;
import petalk.mvp.application.auth.response.AuthUserResponse;
import petalk.mvp.domain.auth.AccessToken;
import petalk.mvp.domain.auth.SocialType;

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
        private AccessToken token;
        private SocialType socialType;

        private AuthenticateCommand(AccessToken token, SocialType socialType) {
            this.token = token;
            this.socialType = socialType;
        }

        public static AuthenticateCommand from(String tokenValue, String tokenType, String socialTypeName, AuthenticateValidator validator) {
            AccessToken token = AccessToken.of(tokenValue, tokenType);
            SocialType socialType = SocialType.from(socialTypeName);

            AuthenticateCommand authenticateCommand = new AuthenticateCommand(token, socialType);

            validator.validate(authenticateCommand);

            return authenticateCommand;
        }

        public AccessToken getToken() {
            return token;
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

        public static AuthenticateResponse from(AuthUserResponse user) {
            return new AuthenticateResponse(user);
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
