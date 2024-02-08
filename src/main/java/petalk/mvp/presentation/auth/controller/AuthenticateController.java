package petalk.mvp.presentation.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase.AuthenticateCommand;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase.AuthenticateResponse;
import petalk.mvp.application.auth.command.in.RegisterSessionUsecase;
import petalk.mvp.application.auth.command.in.RegisterSessionUsecase.RegisterSessionCommand;
import petalk.mvp.application.auth.command.validator.AuthenticateValidator;
import petalk.mvp.application.auth.response.AuthUserResponse;
import petalk.mvp.core.ApiResponse;

@Tag(name = "authenticate", description = "인증 API")
@RestController
@RequiredArgsConstructor
public class AuthenticateController {

    private final AuthenticateUsecase authenticateUsecase;
    private final AuthenticateValidator authenticateValidator;
    private final RegisterSessionUsecase registerSessionUsecase;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticateController.class);


    @Operation(
            summary = "authenticate by social",
            description = """
                    소셜 로그인을 통해 인증합니다.  
                    
                    각 소셜의 OAuth2 인증 코드를 받아서 인증합니다.  
                    
                    인증 완료 후 쿠키에 세션을 등록합니다.  
                    
                    쿠키 세션은 마지막 히트 후 1시간 동안 유효합니다.  
                    
                    **서비스 별 리다이렉트 URL**  
                    
                    | 서비스      | Reason              |
                    | ---------------- | ------------ |
                    | 네이버             | /api/auth/login/oauth2/naver     |
                    | 카카오             | /api/auth/login/oauth2/kakao |
                    | 구글              | /api/auth/login/oauth2/google  |
                    """)
    @PostMapping(
            value = "/auth/authenticate/{provider}",
            produces = "application/json; charset=UTF-8")
    public ResponseEntity<ApiResponse<Response>> authenticate(
            @RequestBody Request request,
            @PathVariable
            @Schema(description = "social type", example = "naver, kakao, google", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank(message = "소셜 타입은 필수입니다.")
            String provider,
            HttpServletRequest httpServletRequest) {
        logger.info("request: {}", request);

        AuthenticateCommand command = AuthenticateCommand.from(request.getCode(), provider, authenticateValidator);
        AuthenticateResponse response = authenticateUsecase.authenticate(command);

        logger.info("authenticate response: {}", response);

        AuthUserResponse user = response.getUser();

        registerSessionUsecase.registerSession(RegisterSessionCommand.from(user.getUserId(), user.getUserAuthority(), httpServletRequest));

        return ResponseEntity.ok(ApiResponse.ok(new Response(response.getUser())));
    }

    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        @NotBlank(message = "코드는 필수입니다.")
        @Schema(description = "인증 코드 입니다.", example = "code", requiredMode = Schema.RequiredMode.REQUIRED)
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
