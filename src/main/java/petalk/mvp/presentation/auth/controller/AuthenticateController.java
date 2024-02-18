package petalk.mvp.presentation.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
import petalk.mvp.presentation.support.ApiResult;

@Tag(name = "authenticate", description = "인증 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticateController {

    private final AuthenticateUsecase authenticateUsecase;
    private final AuthenticateValidator authenticateValidator;
    private final RegisterSessionUsecase registerSessionUsecase;

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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 성공"),
            @ApiResponse(responseCode = "400+", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(schema = @Schema(implementation = ApiResult.class)))})
    @PostMapping(
            value = "/auth/authenticate/{provider}",
            produces = "application/json; charset=UTF-8")
    public ResponseEntity<ApiResult<Result>> authenticate(
            @RequestBody Request request,
            @PathVariable
            @Schema(description = "social type", example = "naver, kakao, google", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank(message = "소셜 타입은 필수입니다.")
            String provider,
            HttpServletRequest httpServletRequest) {

        AuthenticateCommand command = AuthenticateCommand.from(request.getAccessToken(), request.getTokenType(), provider, authenticateValidator);
        AuthenticateResponse response = authenticateUsecase.authenticate(command);

        AuthUserResponse user = response.getUser();

        registerSessionUsecase.registerSession(RegisterSessionCommand.from(user.getUserId(), user.getUserAuthority(), httpServletRequest));

        return ResponseEntity.ok(ApiResult.ok(new Result(response)));
    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        @NotBlank(message = "토큰 값은 필수입니다.")
        @Schema(description = "액세스 토큰입니다.", example = "accessToken", requiredMode = Schema.RequiredMode.REQUIRED)
        private String accessToken;

        @NotBlank(message = "토큰 타입은 필수입니다.")
        @Schema(description = "토큰 타입입니다.", example = "bearer", requiredMode = Schema.RequiredMode.REQUIRED)
        private String tokenType;

        public Request(String accessToken, String tokenType) {
            this.accessToken = accessToken;
            this.tokenType = tokenType;
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Result {
        AuthUserDocsResponse user;

        public Result(AuthenticateResponse response) {
            this.user = AuthUserDocsResponse.from(response.getUser());
        }
    }
}
