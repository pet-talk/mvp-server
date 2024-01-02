package petalk.mvp.auth.present.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import petalk.mvp.auth.application.command.in.AuthenticateUsecase;
import petalk.mvp.auth.application.command.in.AuthenticateUsecase.AuthenticateCommand;
import petalk.mvp.auth.application.command.in.AuthenticateUsecase.AuthenticateResponse;
import petalk.mvp.auth.application.command.in.RegisterSessionUsecase;
import petalk.mvp.auth.application.command.in.RegisterSessionUsecase.RegisterSessionCommand;
import petalk.mvp.auth.application.command.validator.AuthenticateValidator;
import petalk.mvp.auth.application.response.AuthUserResponse;

@RestController
@RequiredArgsConstructor
public class AuthenticateController {

    private final AuthenticateUsecase authenticateUsecase;
    private final AuthenticateValidator authenticateValidator;
    private final RegisterSessionUsecase registerSessionUsecase;

    @PostMapping("/auth/authenticate/{provider}")
    public ResponseEntity<Response> authenticate(@RequestBody Request request, @PathVariable String provider, HttpServletRequest httpServletRequest) {
        AuthenticateCommand command = AuthenticateCommand.from(request.getCode(), provider, authenticateValidator);
        AuthenticateResponse response = authenticateUsecase.authenticate(command);
        AuthUserResponse user = response.getUser();

        registerSessionUsecase.registerSession(RegisterSessionCommand.from(user.getUserId().toString(), user.getUserAuthority(), httpServletRequest));

        Response apiResponse = new Response(response.getUser());

        return ResponseEntity.ok(apiResponse);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        private String code;

        public Request(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        AuthUserResponse user;

        public Response(AuthUserResponse user) {
            this.user = user;
        }
    }
}
