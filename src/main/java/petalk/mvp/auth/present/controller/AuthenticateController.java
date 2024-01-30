package petalk.mvp.auth.present.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
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

@Tag(name = "authenticate", description = "인증 API")
@RestController
@RequiredArgsConstructor
public class AuthenticateController {

    private final AuthenticateUsecase authenticateUsecase;
    private final AuthenticateValidator authenticateValidator;
    private final RegisterSessionUsecase registerSessionUsecase;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticateController.class);

    @Operation(summary = "authenticate by social", description = "소셜 인증을 진행합니다.")
    @PostMapping("/auth/authenticate/{provider}")
    public ResponseEntity<Response> authenticate(
            @RequestBody Request request,
            @PathVariable
            @Schema(description = "social type", example = "naver")
            @NotBlank(message = "소셜 타입은 필수입니다.")
            String provider,
            HttpServletRequest httpServletRequest) {
        logger.info("authenticate request: {}", request);

        AuthenticateCommand command = AuthenticateCommand.from(request.getCode(), provider, authenticateValidator);
        AuthenticateResponse response = authenticateUsecase.authenticate(command);
        logger.info("authenticate response: {}", response);

        AuthUserResponse user = response.getUser();

        registerSessionUsecase.registerSession(RegisterSessionCommand.from(user.getUserId(), user.getUserAuthority(), httpServletRequest));

        Response apiResponse = new Response(response.getUser());

        return ResponseEntity.ok(apiResponse);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        @NotBlank(message = "코드는 필수입니다.")
        @Schema(description = "authenticate code", example = "code")
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

        public AuthUserResponse getUser() {
            return user;
        }

    }
}
