package petalk.mvp.application.auth.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase;
import petalk.mvp.application.auth.command.out.*;
import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.domain.auth.SocialAuthUser;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 인증을 담당하는 서비스입니다.
 */
@Service
@Transactional
public class AuthenticateService implements AuthenticateUsecase {

    private final LoadSocialUserPort loadSocialUserPort;
    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;
    private final Clock clock;

    @Autowired
    public AuthenticateService(LoadSocialUserPort loadSocialUserPort, LoadUserPort loadUserPort, RegisterUserPort registerUserPort, Clock clock) {
        this.loadSocialUserPort = loadSocialUserPort;
        this.loadUserPort = loadUserPort;
        this.registerUserPort = registerUserPort;
        this.clock = clock;
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateCommand command) {
        SocialAuthUser socialAuthUser = loadSocialUserPort.loadSocialUser(command.getCode(), command.getSocialType())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        Optional<AuthUser> userOptional = loadUserPort.loadUser(socialAuthUser);

        if (userOptional.isPresent()) {
            return AuthenticateResponse.from(userOptional.get());
        }

        AuthUser registerUser = socialAuthUser.registerUser(LocalDateTime.now(clock));

        registerUserPort.registerUser(registerUser);

        return AuthenticateResponse.from(registerUser);
    }

}
