package petalk.mvp.auth.domain.command;

import org.springframework.stereotype.Service;
import petalk.mvp.auth.domain.command.in.RegisterSessionUsecase;
import petalk.mvp.auth.domain.command.out.RegisterSessionPort;

@Service
public class RegisterSessionService implements RegisterSessionUsecase {

    private final RegisterSessionPort registerSessionPort;

    public RegisterSessionService(RegisterSessionPort registerSessionPort) {
        this.registerSessionPort = registerSessionPort;
    }

    @Override
    public void registerSession(RegisterSessionCommand command) {
        SessionUserInfo user = SessionUserInfo.register(command.getUserId(), command.getUserAuthority());
        registerSessionPort.registerSession(user, command.getServletRequest());
    }
}
