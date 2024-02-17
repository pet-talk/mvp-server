package petalk.mvp.acceptance.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import petalk.mvp.acceptance.auth.apiTester.AuthenticateApiTester;
import petalk.mvp.core.acceptance.utils.AbstractAcceptanceTest;
import petalk.mvp.core.annotation.AcceptanceTest;


@AcceptanceTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("네이버 인증 요청 인수 테스트")
public class NaverAuthenticateAcceptanceTest extends AbstractAcceptanceTest {

    @Autowired
    AuthenticateApiTester apiTester;

    /**
     * @when 인증 요청을 할 때
     * @then 회원 인증이 가능하다.
     */
    @Test
    @DisplayName("회원가입이 되어있지 않더라도 소셜 계정으로 인증 요청을 하면 회원 인증이 가능하다.")
    void whenHaveSocialIdThenCanAuthenticate() {

        //when
        ExtractableResponse<Response> 인증_요청_응답 = 네이버에게_인증요청();

        //then
        apiTester.인증_성공(인증_요청_응답);
    }

    private ExtractableResponse<Response> 네이버에게_인증요청() {
        return apiTester.인증_요청("naver", "accessToken", "tokenType");
    }

    /**
     * @when 인증 요청을 할 때
     * @then 닉네임이 소셜계정 정보 기반으로 자동 설정된다.
     */
    @Test
    @DisplayName("서비스 계정이 없다면 인증 요청을 할 때 닉네임이 자동으로 설정된다.")
    void whenIdIsNotExistThenNicknameIsSetUpBySocialInfo() throws Exception {

        //when
        ExtractableResponse<Response> 인증_요청_응답 = 네이버에게_인증요청();

        //then
        apiTester.요청_닉네임_체크(인증_요청_응답);
    }

    /**
     * @when 인증 요청을 할 때
     * @then 응답에서 현재 계정의 권한과 id와 닉네임을 확인할 수 있다.
     */
    @Test
    @DisplayName("계정이 존재한다면 인증 요청을 할 때 응답에서 현재 계정의 권한, id, 닉네임을 확인할 수 있다.")
    void whenIdIsExistThenCanCheckAuthorityAndIdAndNickname() throws Exception {

        //when
        ExtractableResponse<Response> 인증_요청_응답 = 네이버에게_인증요청();

        //then
        apiTester.요청_아이디_권한_체크(인증_요청_응답);
    }


    /**
     * @when 인증 요청을 할 때
     * @then 응답에서 현재 요청의 세션 id을 확인할 수 있다.
     */
    @Test
    @DisplayName("소셜 계정이 존재한다면 인증 요청을 할 때 응에서 현재 요청의 세션 id를 확인할 수 있다.")
    void whenAuthenticateThenCanCheck() {

        //when
        ExtractableResponse<Response> 인증_요청_응답 = 네이버에게_인증요청();

        //then
        apiTester.요청_세션_체크(인증_요청_응답);
    }
}
