package petalk.mvp.application.auth.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase;
import petalk.mvp.application.auth.command.out.*;
import petalk.mvp.application.auth.command.validator.AuthenticateValidator;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.domain.auth.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    private final Clock clock = Clock.fixed(Instant.parse("2020-01-01T00:00:00Z"), ZoneId.systemDefault());

    private final AuthenticateService authenticateService = new AuthenticateService(loadSocialUserPort, loadUserPort, registerUserPort, clock);


    private static NaverSocialAuthUser createNaverSocialUser() {
        return NaverSocialAuthUser.from(SocialAuthId.from("id"), "email", "nickname", "name");
    }

    /**
     * @given 인증된 소셜 사용자가 존재하고
     * @given 유저 소셜 정보가 존재하고
     * @given 사용자가 존재한다면
     * @when 인증 요청을 할 때
     * @then 유저정보를 반환한다.
     */
    @Test
    @DisplayName("인증 요청을 하면 유저 정보를 반환한다.")
    void returnUser() {
        //given
        String nickname = "nickname";
        NaverSocialAuthUser naverSocialUser = createNaverSocialUser(nickname);

        given(loadSocialUserPort.loadSocialUser(any(), any()))
                .willReturn(Optional.of(naverSocialUser));

        //given
        UUID userId = UUID.randomUUID();
        UserAuthority authority = UserAuthority.VET;
        AuthUser user = createUser(userId, nickname, authority);
        given(loadUserPort.loadUser(any()))
                .willReturn(Optional.of(user));

        //when
        AuthenticateUsecase.AuthenticateCommand command = this.createCommand();

        AuthenticateUsecase.AuthenticateResponse authenticate = authenticateService.authenticate(command);

        //then

        assertThat(authenticate)
                .extracting("user").isNotNull()
                .extracting("userId", "nickname", "userAuthority")
                .containsExactly(userId, nickname, authority.name());
    }

    private NaverSocialAuthUser createNaverSocialUser(String nickname) {
        String socialId = "id";
        String email = "email";
        String name = "name";

        return NaverSocialAuthUser.from(SocialAuthId.from(socialId), email, nickname, name);
    }

    private AuthUser createUser(UUID id, String nickname, UserAuthority authority) {
        String socialId = "id";
        String email = "email";
        String name = "name";
        return AuthUser.exist(AuthUser.UserId.from(id), createUserSocialInfo(email, socialId, name), nickname, authority, LocalDateTime.now());
    }

    private UserSocialInfo createUserSocialInfo(String email, String socialId, String socialName) {
        return UserSocialInfo.exist(UserSocialInfo.SocialInfoId.from(123L), email, SocialType.NAVER, SocialAuthId.from(socialId), socialName);
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
        given(loadSocialUserPort.loadSocialUser(any(), any()))
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
        given(loadSocialUserPort.loadSocialUser(any(), any()))
                .willReturn(Optional.empty());

        //when

        //then
        AuthenticateUsecase.AuthenticateCommand command = this.createCommand();

        Assertions.assertThatThrownBy(() -> authenticateService.authenticate(command))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    private AuthenticateUsecase.AuthenticateCommand createCommand() {
        String token = "token";
        String tokenType = "bearer";

        String social = "naver";
        AuthenticateValidator validator = new AuthenticateValidator();
        return AuthenticateUsecase.AuthenticateCommand.from(token, tokenType, social, validator);
    }
}