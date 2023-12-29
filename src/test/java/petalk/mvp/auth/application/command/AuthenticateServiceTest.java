package petalk.mvp.auth.application.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import petalk.mvp.auth.application.command.in.AuthenticateUsecase;
import petalk.mvp.auth.application.command.out.*;
import petalk.mvp.auth.application.command.validator.AuthenticateValidator;
import petalk.mvp.auth.domain.NaverSocialAuthUser;
import petalk.mvp.auth.domain.SocialAuthId;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.domain.UserAuthority;
import petalk.mvp.core.annotation.UnitTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

/**
 * 인증 요청에 대한 서비스를 테스트합니다.
 */
@UnitTest
@DisplayName("인증 요청에 대한 서비스 단위 테스트")
class AuthenticateServiceTest {

    private final LoadSocialUserPort loadSocialUserPort = Mockito.mock(LoadSocialUserPort.class);
    private final LoadUserPort loadUserPort = Mockito.mock(LoadUserPort.class);
    private final RegisterUserPort registerUserPort = Mockito.mock(RegisterUserPort.class);
    private final RegisterSessionPort registerSessionPort = Mockito.mock(RegisterSessionPort.class);
    private final RegisterSocialInfoPort registerSocialInfoPort = Mockito.mock(RegisterSocialInfoPort.class);

    private final AuthenticateService authenticateService = new AuthenticateService(loadSocialUserPort, loadUserPort, registerUserPort, registerSessionPort, registerSocialInfoPort);

    /**
     * @given 인증된 소셜 사용자가 존재하고
     * @given 사용자가 존재한다면
     * @when 인증 요청을 할 때
     * @then 세션정보를 반환한다.
     */
    @Test
    @DisplayName("인증 요청을 하면 세션 정보를 반환한다.")
    void returnSession() {
        //given
        given(loadSocialUserPort.loadSocialUser(any()))
                .willReturn(Optional.of(createNaverSocialUser()));

        //given
        UUID uuid = UUID.randomUUID();
        User user = User.exist(User.UserId.from(uuid), "nickname", UserAuthority.VET, LocalDateTime.now());
        given(loadUserPort.loadUser(any()))
                .willReturn(Optional.of(user));

        //when
        AuthenticateUsecase.AuthenticateCommand command = this.createCommand();

        AuthenticateUsecase.AuthenticateResponse authenticate = authenticateService.authenticate(command);

        //then
        then(registerSessionPort)
                .should(times(1))
                .registerSession(any());

        assertThat(authenticate).isNotNull()
                .extracting("session").isNotNull()
                .extracting("sessionId").isNotNull()
                .isInstanceOf(UUID.class);
    }

    private static NaverSocialAuthUser createNaverSocialUser() {
        return NaverSocialAuthUser.from(SocialAuthId.from("id"), "email", "nickname", "name");
    }

    /**
     * @given 인증된 소셜 사용자가 존재하고
     * @given 사용자가 존재한다면
     * @when 인증 요청을 할 때
     * @then 유저정보를 반환한다.
     */
    @Test
    @DisplayName("인증 요청을 하면 유저 정보를 반환한다.")
    void returnUser() {
        //given
        given(loadSocialUserPort.loadSocialUser(any()))
                .willReturn(Optional.of(createNaverSocialUser()));

        //given
        UUID uuid = UUID.randomUUID();
        User user = User.exist(User.UserId.from(uuid), "nickname", UserAuthority.VET, LocalDateTime.now());
        given(loadUserPort.loadUser(any()))
                .willReturn(Optional.of(user));

        //when
        AuthenticateUsecase.AuthenticateCommand command = this.createCommand();

        AuthenticateUsecase.AuthenticateResponse authenticate = authenticateService.authenticate(command);

        //then

        assertThat(authenticate)
                .extracting("session").isNotNull()
                .extracting("user").isNotNull()
                .extracting("userId", "nickname", "userAuthority")
                .containsExactly(user.getId().getValue(), user.getNickname(), user.getUserAuthority().name());
    }

    /**
     * @given 인증된 소셜 사용자가 존재하고
     * @given 사용자가 존재하지 않는다면
     * @when 인증 요청을 할 때
     * @then 새 유저를 생성한다.
     */
    @Test
    @DisplayName("인증 요청을 할 때 새 유저를 생성한다.")
    void registerUser() {
        //given
        given(loadSocialUserPort.loadSocialUser(any()))
                .willReturn(Optional.of(createNaverSocialUser()));

        //given
        given(loadUserPort.loadUser(any()))
                .willReturn(Optional.empty());

        //when
        AuthenticateUsecase.AuthenticateCommand command = this.createCommand();

        AuthenticateUsecase.AuthenticateResponse authenticate = authenticateService.authenticate(command);

        //then
        BDDMockito.then(registerUserPort)
                .should(times(1))
                .registerUser(any());
    }

    /**
     * @given 인증된 소셜 사용자가 존재하지 않는다면
     * @when 인증 요청을 할 때
     * @then 에러를 반환한다.
     */
    @Test
    @DisplayName("인증에는 소셜 사용자 정보가 필수다.")
    void dontExistSocialUser() {
        //given
        given(loadSocialUserPort.loadSocialUser(any()))
                .willReturn(Optional.empty());

        //when

        //then
        AuthenticateUsecase.AuthenticateCommand command = this.createCommand();

        Assertions.assertThatThrownBy(() -> authenticateService.authenticate(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    private AuthenticateUsecase.AuthenticateCommand createCommand() {
        String token = "token";
        String social = "naver";
        AuthenticateValidator validator = new AuthenticateValidator();
        return AuthenticateUsecase.AuthenticateCommand.from(token, social, validator);
    }
}