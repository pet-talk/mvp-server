package petalk.mvp.acceptance.auth.apiTester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import petalk.mvp.core.ApiResult;
import petalk.mvp.core.acceptance.utils.TestParam;
import petalk.mvp.core.annotation.ApiTester;
import petalk.mvp.presentation.auth.controller.AuthenticateController.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * 인증 요청 인수 테스트를 위한 api 요청 테스트 클래스입니다.
 */
@ApiTester
public class AuthenticateApiTester {

    @Autowired private ObjectMapper objectMapper;
    private final String PATH = "/api/auth/authenticate";

    private ExtractableResponse<Response> request(String provider, String code) {

        TestParam param = TestParam
                .builder()
                .add("code", code);

        String path = PATH +
                '/' +
                provider;

        return RestAssured
                .given().log().all()
                .body(param.build())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> 인증_요청(String provider, String code) {
        return request(provider, code);
    }

    public void 인증_성공(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public void 요청_닉네임_체크(ExtractableResponse<Response> response) throws JsonProcessingException {

        ApiResult<Result> result = getResult(response);

        assertThat(result)
                .extracting("data.user.nickname")
                .isNotNull();
    }

    private ApiResult<Result> getResult(ExtractableResponse<Response> response) throws JsonProcessingException {
        String json = response.body().asString();
        ApiResult<Result> result = objectMapper.readValue(json, new TypeReference<>(){});
        return result;
    }

    public void 요청_아이디_권한_체크(ExtractableResponse<Response> response) throws JsonProcessingException {

        ApiResult<Result> result = getResult(response);

        assertAll(
                () -> assertThat(result)
                        .extracting("data.user.userAuthority")
                        .isEqualTo("PET_OWNER"),
                () -> assertThat(result)
                        .extracting("data.user")
                        .extracting("userId", "nickname")
                        .isNotNull()
        );
    }



    public void 요청_세션_체크(ExtractableResponse<Response> response) {
        String session = response.cookie("SESSION");

        assertThat(session).isNotNull();
    }
}
