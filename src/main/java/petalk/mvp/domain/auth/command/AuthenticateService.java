package petalk.mvp.domain.auth.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petalk.mvp.domain.auth.command.in.AuthenticateUsecase;
import petalk.mvp.domain.auth.command.out.*;

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
    private final petalk.mvp.domain.auth.command.out.LoadUserSocialInfoPort LoadUserSocialInfoPort;

    @Autowired
    public AuthenticateService(LoadSocialUserPort loadSocialUserPort, LoadUserPort loadUserPort, RegisterUserPort registerUserPort, RegisterSocialInfoPort registerSocialInfoPort, LoadUserSocialInfoPort LoadUserSocialInfoPort) {
        this.loadSocialUserPort = loadSocialUserPort;
        this.loadUserPort = loadUserPort;
        this.registerUserPort = registerUserPort;
        this.registerSocialInfoPort = registerSocialInfoPort;
        this.LoadUserSocialInfoPort = LoadUserSocialInfoPort;
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateCommand command) {
        LocalDateTime now = LocalDateTime.now();

        Authenticator authenticator = Authenticator.of(command.getCode(), command.getSocialType());

        SocialAuthUser socialAuthUser = loadSocialUserPort.loadSocialUser(authenticator)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        Optional<UserSocialInfo> socialInfoOptional = LoadUserSocialInfoPort.loadSocialInfo(socialAuthUser);

        if (socialInfoOptional.isPresent()) {
            UserSocialInfo userSocialInfo = socialInfoOptional.get();

            User user = loadUserPort.loadUser(userSocialInfo)
                    .orElseThrow(() -> new IllegalArgumentException("소셜유저가 존재하지만 유저가 존재하지 않습니다."));

            return AuthenticateResponse.from(user);
        }

        User registerUser = socialAuthUser.registerUser(now);

        registerUserPort.registerUser(registerUser);

        UserSocialInfo userSocialInfo = socialAuthUser.registerInfo(registerUser);

        registerSocialInfoPort.registerSocialInfo(userSocialInfo);

        return AuthenticateResponse.from(registerUser);
    }

}
