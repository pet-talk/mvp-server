package petalk.mvp.application.auth.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase;
import petalk.mvp.application.auth.command.out.*;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.domain.auth.User;
import petalk.mvp.domain.auth.UserSocialInfo;

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
    private final RegisterSocialInfoPort registerSocialInfoPort;
    private final LoadUserSocialInfoPort loadUserSocialInfoPort;
    private final Clock clock;

    @Autowired
    public AuthenticateService(LoadSocialUserPort loadSocialUserPort, LoadUserPort loadUserPort, RegisterUserPort registerUserPort, RegisterSocialInfoPort registerSocialInfoPort, LoadUserSocialInfoPort loadUserSocialInfoPort, Clock clock) {
        this.loadSocialUserPort = loadSocialUserPort;
        this.loadUserPort = loadUserPort;
        this.registerUserPort = registerUserPort;
        this.registerSocialInfoPort = registerSocialInfoPort;
        this.loadUserSocialInfoPort = loadUserSocialInfoPort;
        this.clock = clock;
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateCommand command) {
        SocialAuthUser socialAuthUser = loadSocialUserPort.loadSocialUser(command.getCode(), command.getSocialType())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        Optional<UserSocialInfo> socialInfoOptional = loadUserSocialInfoPort.loadSocialInfo(socialAuthUser);

        if (socialInfoOptional.isPresent()) {
            return socialInfoOptional
                    .flatMap(loadUserPort::loadUser)
                    .map(AuthenticateResponse::from)
                    .orElseThrow(() -> new IllegalArgumentException("소셜유저가 존재하지만 유저가 존재하지 않습니다."));
        }

        User registerUser = socialAuthUser.registerUser(LocalDateTime.now(clock));
        UserSocialInfo userSocialInfo = socialAuthUser.registerInfo(registerUser);

        registerUserPort.registerUser(registerUser);
        registerSocialInfoPort.registerSocialInfo(userSocialInfo);

        return AuthenticateResponse.from(registerUser);
    }

}
