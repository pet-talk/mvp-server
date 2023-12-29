package petalk.mvp.auth.application.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petalk.mvp.auth.application.command.in.AuthenticateUsecase;
import petalk.mvp.auth.application.command.out.*;
import petalk.mvp.auth.domain.*;

import java.time.LocalDateTime;

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
    private final RegisterSocialInfoPort registerSocialInfoPort;

    @Autowired
    public AuthenticateService(LoadSocialUserPort loadSocialUserPort, LoadUserPort loadUserPort, RegisterUserPort registerUserPort, RegisterSessionPort registerSessionPort, RegisterSocialInfoPort registerSocialInfoPort) {
        this.loadSocialUserPort = loadSocialUserPort;
        this.loadUserPort = loadUserPort;
        this.registerUserPort = registerUserPort;
        this.registerSessionPort = registerSessionPort;
        this.registerSocialInfoPort = registerSocialInfoPort;
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateCommand command) {
        LocalDateTime now = LocalDateTime.now();

        Authenticator authenticator = Authenticator.of(command.getToken(), command.getSocialType());

        SocialAuthUser socialAuthUser = loadSocialUserPort.loadSocialUser(authenticator)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        User user = loadUserPort.loadUser(socialAuthUser)
                .orElseGet(() -> User.register(socialAuthUser, now));

        if (user.isNew()) {
            registerUserPort.registerUser(user);

            UserSocialInfo userSocialInfo = UserSocialInfo.register(user, socialAuthUser);
            registerSocialInfoPort.registerSocialInfo(userSocialInfo);
        }

        Session session = Session.register(user, LocalDateTime.now());
        registerSessionPort.registerSession(session);

        return AuthenticateUsecase.AuthenticateResponse.from(session);
    }

}
