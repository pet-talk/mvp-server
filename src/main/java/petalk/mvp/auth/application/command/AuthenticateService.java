package petalk.mvp.auth.application.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petalk.mvp.auth.application.command.in.AuthenticateUsecase;
import petalk.mvp.auth.application.command.out.LoadSocialUserPort;
import petalk.mvp.auth.application.command.out.LoadUserPort;
import petalk.mvp.auth.application.command.out.RegisterSessionPort;
import petalk.mvp.auth.application.command.out.RegisterUserPort;
import petalk.mvp.auth.domain.*;

/**
 * 인증을 담당하는 서비스입니다.
 */
@Service
@Transactional
public class AuthenticateService implements AuthenticateUsecase {

    private final LoadSocialUserPort loadSocialUserPort;
    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;
    private final RegisterSessionPort registerSessionPort;

    public AuthenticateService(LoadSocialUserPort loadSocialUserPort, LoadUserPort loadUserPort, RegisterUserPort registerUserPort, RegisterSessionPort registerSessionPort) {
        this.loadSocialUserPort = loadSocialUserPort;
        this.loadUserPort = loadUserPort;
        this.registerUserPort = registerUserPort;
        this.registerSessionPort = registerSessionPort;
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateCommand command) {
        Authenticator authenticator = Authenticator.of(command.getToken(), command.getSocialType());

        SocialAuthUser socialAuthUser = loadSocialUserPort.loadSocialUser(authenticator)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        User user = loadUserPort.loadUser(socialAuthUser)
                .orElseGet(() -> {
                    User newUser = User.register();
                    registerUserPort.registerUser(newUser);
                    return newUser;
                });

        Session session = Session.register();
        registerSessionPort.registerSession(session, user);

        return AuthenticateUsecase.AuthenticateResponse.from(session, user);
    }

}
