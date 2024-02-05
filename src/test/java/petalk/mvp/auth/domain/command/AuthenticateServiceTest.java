package petalk.mvp.auth.domain.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import petalk.mvp.domain.auth.command.*;
import petalk.mvp.domain.auth.command.out.*;
import petalk.mvp.domain.auth.command.in.AuthenticateUsecase;
import petalk.mvp.core.annotation.UnitTest;

import java.time.LocalDateTime;
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
    private final RegisterSocialInfoPort registerSocialInfoPort = Mockito.mock(RegisterSocialInfoPort.class);
    private final LoadUserSocialInfoPort loadUserSocialInfoPort = Mockito.mock(LoadUserSocialInfoPort.class);

    private final AuthenticateService authenticateService = new AuthenticateService(loadSocialUserPort, loadUserPort, registerUserPort, registerSocialInfoPort, loadUserSocialInfoPort);


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
        NaverSocialAuthUser naverSocialUser = createNaverSocialUser();

        given(loadSocialUserPort.loadSocialUser(any()))
                .willReturn(Optional.of(naverSocialUser));

        //given
        UserSocialInfo userSocialInfo = createUserSocialInfo(naverSocialUser);
        given(loadUserSocialInfoPort.loadSocialInfo(any()))
                .willReturn(Optional.of(userSocialInfo));

        //given
        User user = createUser(userSocialInfo);
        given(loadUserPort.loadUser(any()))
                .willReturn(Optional.of(user));

        //when
        AuthenticateUsecase.AuthenticateCommand command = this.createCommand();

        AuthenticateUsecase.AuthenticateResponse authenticate = authenticateService.authenticate(command);

        //then

        assertThat(authenticate)
                .extracting("user").isNotNull()
                .extracting("userId", "nickname", "userAuthority")
                .containsExactly(user.getId().getValue(), user.getNickname(), user.getUserAuthority().name());
    }

    private static User createUser(UserSocialInfo userSocialInfo) {
        return User.exist(userSocialInfo.getUserId(), userSocialInfo.getSocialName(), UserAuthority.VET, LocalDateTime.now());
    }

    private UserSocialInfo createUserSocialInfo(NaverSocialAuthUser naverSocialUser) {
        return UserSocialInfo.exist(UserSocialInfo.SocialInfoId.from(1L), User.UserId.from(UUID.randomUUID()), naverSocialUser.getEmail(), SocialType.NAVER, naverSocialUser.getSocialId(), naverSocialUser.getName());
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
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    private AuthenticateUsecase.AuthenticateCommand createCommand() {
        String token = "token";
        String social = "naver";
        AuthenticateValidator validator = new AuthenticateValidator();
        return AuthenticateUsecase.AuthenticateCommand.from(token, social, validator);
    }
}