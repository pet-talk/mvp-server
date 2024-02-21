package petalk.mvp.application.auth.command;

import org.springframework.stereotype.Service;
import petalk.mvp.application.auth.command.in.RegisterSessionUsecase;
import petalk.mvp.application.auth.command.out.RegisterSessionPort;
import petalk.mvp.domain.auth.SessionUserInfo;

@Service
public class RegisterSessionService implements RegisterSessionUsecase {

    private final RegisterSessionPort registerSessionPort;

    public RegisterSessionService(RegisterSessionPort registerSessionPort) {
        this.registerSessionPort = registerSessionPort;
    }

    @Override
    public void registerSession(RegisterSessionCommand command) {
        SessionUserInfo user = SessionUserInfo.register(command.getUserId(), command.getUserAuthority());
        registerSessionPort.registerSession(user);
    }
}
