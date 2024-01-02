package petalk.mvp.auth.application.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petalk.mvp.auth.application.command.in.AuthenticateUsecase;
import petalk.mvp.auth.application.command.out.LoadSocialUserPort;
import petalk.mvp.auth.application.command.out.LoadUserPort;
import petalk.mvp.auth.application.command.out.RegisterSocialInfoPort;
import petalk.mvp.auth.application.command.out.RegisterUserPort;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.domain.SocialAuthUser;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.domain.UserSocialInfo;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * 인증을 담당하는 서비스입니다.
 */
@Service
@Transactional
public class AuthenticateService implements AuthenticateUsecase {

    private final LoadSocialUserPort loadSocialUserPort;
    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;
    private final RegisterSocialInfoPort registerSocialInfoPort;

    @Autowired
    public AuthenticateService(LoadSocialUserPort loadSocialUserPort, LoadUserPort loadUserPort, RegisterUserPort registerUserPort, RegisterSocialInfoPort registerSocialInfoPort) {
        this.loadSocialUserPort = loadSocialUserPort;
        this.loadUserPort = loadUserPort;
        this.registerUserPort = registerUserPort;
        this.registerSocialInfoPort = registerSocialInfoPort;
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateCommand command) {
        LocalDateTime now = LocalDateTime.now();

        Authenticator authenticator = Authenticator.of(command.getCode(), command.getSocialType());

        SocialAuthUser socialAuthUser = loadSocialUserPort.loadSocialUser(authenticator)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        User user = loadUserPort.loadUser(socialAuthUser)
                .orElseGet(() -> User.register(socialAuthUser, now));

        if (user.isNew()) {
            registerUserPort.registerUser(user);

            UserSocialInfo userSocialInfo = UserSocialInfo.register(user, socialAuthUser);
            registerSocialInfoPort.registerSocialInfo(userSocialInfo);
        }
        return AuthenticateResponse.from(user);
    }

}
