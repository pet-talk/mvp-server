package petalk.mvp.application.auth.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import petalk.mvp.application.auth.command.validator.AuthenticateValidator;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.core.errors.ValidationErrorException;

import static org.assertj.core.groups.Tuple.tuple;

@UnitTest
@DisplayName("인증 요청 검증에 대한 서비스 단위 테스트")
class AuthenticateValidatorTest {

    /**
     * @given token 값이 존재하고
     * @given social 값이 존재한다면
     * @when 인증 요청을 할 때
     * @then 인증 요청 커맨드가 생성된다.
     */
    @Test
    @DisplayName("인증 요청을 검증한다.")
    void validateAuthenticateRequest() {
        //given
        String token = "token";
        String tokenType = "bearer";

        //given
        String social = "naver";

        //when
        //then
        AuthenticateValidator validator = new AuthenticateValidator();
        AuthenticateUsecase.AuthenticateCommand command = AuthenticateUsecase.AuthenticateCommand.from(token, tokenType, social, validator);

    }

    /**
     * @given token 값이 " "이고
     * @given social 값이 존재한다면
     * @when 인증 요청을 할 때
     * @then 인증 요청 커맨드가 생성되지 않는다.
     */
    @Test
    @DisplayName("인증 요청은 공백을 허용하지 않는다.")
    void cantRequestBlank() {
        //given
        String token = " ";
        String tokenType = " ";
        //given
        String social = "naver";

        //when
        //then
        AuthenticateValidator validator = new AuthenticateValidator();
        Assertions.assertThatThrownBy(() -> AuthenticateUsecase.AuthenticateCommand.from(token, tokenType, social, validator))
                .isInstanceOf(ValidationErrorException.class)
                .extracting("errors")
                .extracting("errors").asList()
                .hasSize(2)
                .extracting("field", "message")
                .containsExactly(tuple("tokenValue", "토큰 값이 비어있습니다."), tuple("tokenType", "토큰 타입이 비어있습니다."));
    }

    /**
     * @given token 값이 존재하고이고
     * @given social 값이 존재하지 않다면
     * @when 인증 요청을 할 때
     * @then 인증 요청 커맨드가 생성되지 않는다.
     */
    @Test
    @DisplayName("인증 요청은 지원하지 않은 소셜 로그인을 허용하지 않는다.")
    void cantRequestNotExistSocial() {
        //given
        String token = "fasdfasdf";
        String tokenType = "bearer";

        //given
        String social = "daum";

        //when
        //then
        AuthenticateValidator validator = new AuthenticateValidator();
        Assertions.assertThatThrownBy(() -> AuthenticateUsecase.AuthenticateCommand.from(token, tokenType, social, validator))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting( "message")
                .isEqualTo("잘못된 소셜 타입입니다.");
    }
}